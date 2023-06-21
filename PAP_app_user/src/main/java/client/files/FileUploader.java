package client.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import client.ServerInformation;
import client.Music.MusicManager;

public class FileUploader implements Runnable {
	private FileInputStream input;
	private String uuid;
	private UploadAccessors accessors;

	public FileUploader(File file, String uuid, UploadAccessors accessors) throws FileNotFoundException {
		input = new FileInputStream(file);
		this.uuid = uuid;
		this.accessors = accessors;
	}

	@Override
	public void run() {
		Socket socket = null;
		try {
			socket = new Socket(ServerInformation.SERVER_IP.value(), ServerInformation.FILE_PORT.intValue());
			socket.getOutputStream().write(uuid.getBytes());
			byte[] buffer = new byte[1024 * 1024];
			int bytesRead = input.read(buffer);
			ByteBuffer byteBuffer = ByteBuffer.allocate(4);
			while (bytesRead != -1) {
				socket.getOutputStream().write(byteBuffer.putInt(0, bytesRead).array());
				socket.getOutputStream().write(buffer, 0, bytesRead);
				socket.getInputStream().skip(2);
				bytesRead = input.read(buffer);
			}
			accessors.finishUpload(uuid);
			MusicManager.updateUserSongsList();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
