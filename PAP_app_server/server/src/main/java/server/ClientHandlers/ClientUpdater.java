package server.ClientHandlers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.tools.javac.Main;

import server.Chat.Chat;
import server.Chat.RequestTypes;
import server.DatabaseInteractors.MessageDataAccesor;
import server.Login.Login;
import server.Login.LoginRequestTypes;
import server.Music.MusicRequestHandler;
import server.Music.MusicRequestTypes;
import server.files.UploadRequestProcessor;
import server.files.UploadRequestTypes;

public class ClientUpdater implements Runnable {
	List<Client> clients = new ArrayList<Client>();
	List<Client> waitingClients = new ArrayList<Client>();
	boolean running = true;

	public ClientUpdater() {

	}

	public void addClient(Socket client) throws IOException {
		synchronized (clients) {
			waitingClients.add(new Client(client));
			System.out.println("Added client.");
		}
	}

	public void stop() {
		running = false;
	}
	
	public void run() {
		ClientUpdaterAcceptor acceptor = new ClientUpdaterAcceptor(this);
		Thread acceptorThread = new Thread(acceptor);
		acceptorThread.run();
		try {
			ServerSocket server = new ServerSocket(8001);
			while (true) {
				Instant start = Instant.now();
				synchronized (clients) {
					ListIterator<Client> it = waitingClients.listIterator();
					// System.out.println("Processing clients.");
					while (it.hasNext()) {
						// System.out.println("Processing client.");
						Client client = it.next();
						try {
							if (client.hasMessage()) {
								handleClient(client);
							}
						} catch (IOException exception) {
							client.close();
						}
						if (client.isClosed()) {
							it.remove();
							System.out.println("Client disconnected.");
						}
					}
				}
				Instant finish = Instant.now();
				long time = Duration.between(start, finish).toNanos();
				if (time < 10000000) {
					time = 10000000 - time;
					try {
						Thread.sleep(time / 1000000);
					} catch (InterruptedException e) {
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(int messageID, String conversationID) {
		synchronized (clients) {
			ListIterator<Client> it = clients.listIterator();
			// System.out.println("Processing clients.");
			while (it.hasNext()) {
				// System.out.println("Processing client.");
				Client client = it.next();
				try {
					if(client.userID == null) {
						client.close();
					}
					else if(client.userID.equals(conversationID)) {
						Hashtable<String, String> message = MessageDataAccesor.getData(messageID);
						Set<String> keys = message.keySet();
			            JSONObject messageJSON = new JSONObject();
			            for (String key : keys) {
			            	messageJSON.put(key, message.get(key));
			            }
			            JSONObject data = new JSONObject();
			            data.put("value", messageJSON);
			            client.write((data.toString() + "\n").getBytes());
					}
				} catch (IOException exception) {
					client.close();
				}
				if (client.isClosed()) {
					it.remove();
					System.out.println("Client disconnected.");
				}
			}
		}
	}

	void handleClient(Client client) throws IOException {
		String message = client.getMessage();
		System.out.println("\tRequest: " + message);
		JSONObject messageJSON = null;
		String typeStr;
		try {
			messageJSON = new JSONObject(message);
			typeStr = messageJSON.getString("type");
		} catch (JSONException e) {
			System.out.println("Received request without type:");
			System.out.println(message);
			return;
		}
		if(!LoginRequestTypes.SEND_LOGIN.value().equals(typeStr)) {
			System.out.println("Received request of incorrect type: " + typeStr);
			return;
		}
		JSONObject value = null;
		try {
			value = messageJSON.getJSONObject("value");
		} catch (JSONException e) {
			System.out.println("Received request without value:");
			System.out.println(message);
			return;
		}
		JSONObject responseJSON = Login.procesRequests(LoginRequestTypes.SEND_LOGIN, value);
		String response = responseJSON.toString() + "\n";
		System.out.print("\tResponse: " + response);
		client.write(response.getBytes());
		if(responseJSON.getJSONObject("value").getBoolean("outcome")) {
			client.userID = responseJSON.getJSONObject("value").getString("user_id");
		}
		else {
			client.close();
		}
	}
}

class ClientUpdaterAcceptor implements Runnable {
	ClientUpdater updater = null;
	
	public ClientUpdaterAcceptor(ClientUpdater updater) {
		this.updater = updater;
	}
	
	public void run() {
		try {
			ServerSocket server = new ServerSocket(8001);
			while (true) {
				updater.addClient(server.accept());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}