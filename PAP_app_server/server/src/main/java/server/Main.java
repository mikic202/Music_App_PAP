package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;

import server.ClientHandlers.ClientHandler;
import server.ConnectionPool.ConnectionPool;
import server.files.FileUploader;

public class Main {
	static ClientHandler handler = new ClientHandler();
	public static FileUploader uploader = new FileUploader();

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		ServerSocket server = new ServerSocket(8000);
		System.out.println("Server has started on 127.0.0.1:8000.\nWaiting for a connection…");
		Thread clientHandlerThread = new Thread(handler);
		clientHandlerThread.start();
		System.out.println("Connections active: " + Integer.toString(ConnectionPool.availableConnections()));
		Thread fileUploaderThread = new Thread(uploader);
		fileUploaderThread.start();
		try {
			while (true) {
				handler.addClient(server.accept());
				System.out.println("Client connected.");
			}

		} finally {
			System.out.println("Server stopped.");
			server.close();
		}
		// UserDataAccesor.getData(1);
	}
}