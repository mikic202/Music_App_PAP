package client.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import client.ServerConnector.ServerConnector;

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
			socket = new Socket("144.91.114.89", 8001);
			socket.getOutputStream().write(uuid.getBytes());
			input.transferTo(socket.getOutputStream());
			accessors.finishUpload(uuid);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
