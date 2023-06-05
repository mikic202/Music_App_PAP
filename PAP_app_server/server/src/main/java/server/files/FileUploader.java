package server.files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.HexFormat;
import java.util.List;
import java.util.ListIterator;

import server.Main;
import server.DatabaseInteractors.FileDataSetter;

public class FileUploader implements Runnable {
	private ArrayList<Socket> waitingSockets = new ArrayList<Socket>();
	private HashMap<String, FileConnector> files = new HashMap<String, FileConnector>();
	private List<UploadingSocket> uploadingSockets = new ArrayList<UploadingSocket>();

	public boolean startUpload(String uuid, String user_id, String file_name, boolean is_image) {
		synchronized (files) {
			try {
				files.put(uuid, new FileConnector(user_id, file_name, uuid, is_image));
			} catch (IOException e) {
				return false;
			}
			return true;
		}
	}

	public boolean finishUpload(String uuid) {
		synchronized (files) {
			if (!files.containsKey(uuid)) {
				return false;
			}
			try {
				files.get(uuid).finish();
			} catch (IOException e) {
				return false;
			}
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				digest.reset();
				String oldPath = "files/upload/" + uuid;
				FileInputStream input = new FileInputStream(oldPath);
				BufferedInputStream bufferedInput = new BufferedInputStream(input);
				DigestInputStream digestInput = new DigestInputStream(bufferedInput, digest);
				try {
					while (digestInput.read() != -1)
						;
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						bufferedInput.close();
					} catch (IOException e) {
					}
				}
				String sha256 = HexFormat.of().formatHex(digest.digest());
				String newPath = "files/" + sha256.substring(0, 1) + "/" + sha256.substring(0, 2) + "/" + sha256;
				Files.createDirectories(Paths.get(newPath).getParent());
				Files.move(Paths.get(oldPath), Paths.get(newPath), REPLACE_EXISTING);
				Hashtable<String, String> fileInfo = new Hashtable<String, String>();
				fileInfo.put("file_name", files.get(uuid).file_name);
				fileInfo.put("user_id", files.get(uuid).user_id);
				fileInfo.put("file_path", newPath);
				fileInfo.put("is_image", files.get(uuid).is_image ? "true" : "false");
				FileDataSetter.addData(fileInfo);
			} catch (FileNotFoundException e) {
				return false;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (IOException e) {
				return false;
			}
			return true;
		}
	}

	public void addClient(Socket socket) {
		synchronized (waitingSockets) {
			waitingSockets.add(socket);
		}
	}

	@Override
	public void run() {
		UploadConnectionAcceptor acceptor = new UploadConnectionAcceptor(this);
		Thread uploadConnectionAcceptorThread = new Thread(acceptor);
		uploadConnectionAcceptorThread.start();
		while (true) {
			Instant start = Instant.now();
			ListIterator<UploadingSocket> itUploading = Main.uploader.uploadingSockets.listIterator();
			// System.out.println("Processing clients.");
			while (itUploading.hasNext()) {
				// System.out.println("Processing client.");
				UploadingSocket uploadingSocket = itUploading.next();
				try {
					if (uploadingSocket.hasMessage()) {
						files.get(uploadingSocket.uuid).write(uploadingSocket.getMessage());
						uploadingSocket.write("OK".getBytes());
					}
				} catch (IOException exception) {
					uploadingSocket.close();
				}
				if (uploadingSocket.isClosed()) {
					itUploading.remove();
					System.out.println("Client disconnected.");
				}
			}
			synchronized (waitingSockets) {
				ListIterator<Socket> itWaiting = Main.uploader.waitingSockets.listIterator();
				while (itWaiting.hasNext()) {
					Socket waitingSocket = itWaiting.next();
					try {
						if (waitingSocket.getInputStream().available() >= 36) {
							String uuid = new String(waitingSocket.getInputStream().readNBytes(36));
							if (files.containsKey(uuid)) {
								uploadingSockets.add(new UploadingSocket(waitingSocket, uuid));
							}
							itWaiting.remove();
						}
					} catch (IOException e) {
						itWaiting.remove();
					}
				}
			}
			Instant finish = Instant.now();
			long time = Duration.between(start, finish).toNanos();
			if (time < 50000000) {
				time = 50000000 - time;
				try {
					Thread.sleep(time / 1000000);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}

class UploadingSocket {
	private Socket socket;
	public String uuid;
	InputStream in;
	OutputStream out;
	byte[] buffer = new byte[1024 * 1024];
	int buffSize = 0;
	int messageSize = -1;
	ByteBuffer byteBuffer = ByteBuffer.allocate(4);

	public UploadingSocket(Socket socket, String uuid) throws IOException {
		this.socket = socket;
		this.uuid = uuid;
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}

	public boolean hasMessage() throws IOException {
		if (messageSize == buffSize) {
			return true;
		}
		if (messageSize == -1) {
			if (in.available() >= 4) {
				byteBuffer.put(0, in.readNBytes(4));
				messageSize = byteBuffer.getInt(0);
			} else {
				return false;
			}
		}
		if (in.available() > 0) {
			int toRead = messageSize - buffSize;
			int available = in.available();
			if (toRead > available) {
				toRead = available;
			}
			int bytesRead = in.read(buffer, buffSize, toRead);
			if (bytesRead == -1) {
				throw new IOException();
			}
			buffSize += bytesRead;
			return buffSize == messageSize;
		}
		return false;
	}

	public byte[] getMessage() throws IOException {
		int size = buffSize;
		buffSize = 0;
		messageSize = -1;
		return Arrays.copyOf(buffer, size);
	}

	public void write(byte[] data) throws IOException {
		out.write(data);
	}

	public boolean isClosed() {
		return socket.isClosed();
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException exception) {
			System.out.println("Warning: IOException closing a socket.");
		}
	}
}

class FileConnector {
	public String user_id;
	public String file_name;
	public boolean is_image;
	private FileOutputStream output;

	public FileConnector(String user_id, String file_name, String uuid, boolean is_image) throws IOException {
		this.user_id = user_id;
		this.file_name = file_name;
		this.is_image = is_image;
		String path = "files/upload/" + uuid;
		Files.createDirectories(Paths.get(path).getParent());
		output = new FileOutputStream(path);
	}

	public void write(byte[] data) throws IOException {
		output.write(data);
	}

	public void finish() throws IOException {
		output.close();
	}
}

class UploadConnectionAcceptor implements Runnable {
	private FileUploader uploader;

	public UploadConnectionAcceptor(FileUploader uploader) {
		this.uploader = uploader;
	}

	@Override
	public void run() {
		ServerSocket server;
		try {
			server = new ServerSocket(8001);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			while (true) {
				uploader.addClient(server.accept());
				System.out.println("Client connected.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Server stopped.");
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}