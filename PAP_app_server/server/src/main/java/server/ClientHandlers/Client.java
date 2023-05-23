package server.ClientHandlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
	Socket socket;
	InputStream in;
	OutputStream out;
	String message = "";
	public String userID = null;

	public Client(Socket socket) throws IOException {
		this.socket = socket;
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}

	public boolean hasMessage() throws IOException {
		if (in.available() > 0) {
			message += new String(in.readNBytes(in.available()), StandardCharsets.UTF_8);
		}
		return message.contains("\n");
	}

	public String getMessage() {
		int index = message.indexOf("\n");
		String result = message.substring(0, index);
		message = message.substring(index + 1);
		return result;
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
