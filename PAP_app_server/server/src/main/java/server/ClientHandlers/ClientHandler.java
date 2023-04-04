package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class ClientHandler implements Runnable {
	List<Client> clients = new ArrayList<Client>();
	boolean running = true;
	
	public ClientHandler() {
		
	}
	
	public void addClient(Socket client) throws IOException {
		synchronized (clients) {
			clients.add(new Client(client));
			System.out.println("Added client.");
		}
	}
	
	public void stop() {
		running = false;
	}

	public void run() {
		while(true) {
			synchronized (clients) {
				ListIterator<Client> it = clients.listIterator();
				//System.out.println("Processing clients.");
				while(it.hasNext()) {
					//System.out.println("Processing client.");
					Client client = it.next();
					try {
						if(client.hasMessage()) {
							handleClient(client);
						}
					}
					catch(IOException exception) {
						client.close();
					}
					if(client.isClosed()) {
						it.remove();
						System.out.println("Client disconnected.");
					}
				}
			}
		}
	}
	
	void handleClient(Client client) throws IOException {
		client.write(("message: " + client.getMessage() + "\n").getBytes());
	}
}

class Client {
	Socket socket;
	InputStream in;
	OutputStream out;
	String message = "";
	
	public Client(Socket socket) throws IOException {
		this.socket = socket;
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}
	
	public boolean hasMessage() throws IOException {
		if(in.available() > 0) {
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
		}
		catch(IOException exception)
		{
			System.out.println("Warning: IOException closing a socket.");
		}
	}
}