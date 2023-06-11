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
import server.DatabaseInteractors.UserDataAccesor;
import server.Login.Login;
import server.Login.LoginRequestTypes;
import server.Music.MusicRequestHandler;
import server.Music.MusicRequestTypes;
import server.ServerConnectionConstants.MessagesTopLevelConstants;
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
			JSONObject response = new JSONObject();
			JSONObject value = new JSONObject();
			value.put("outcome", true);
			response.put(MessagesTopLevelConstants.VALUE.value(), value);
			var out = client.getOutputStream();
			out.write((response.toString() + "\n").getBytes());
			System.out.println("Added client to the updater.");
		}
	}

	public void stop() {
		running = false;
	}

	public void run() {
		System.out.println("Client updater starting...");
		ClientUpdaterAcceptor acceptor = new ClientUpdaterAcceptor(this);
		Thread acceptorThread = new Thread(acceptor);
		acceptorThread.start();
		System.out.println("Client updater started.");
		while (running) {
			Instant start = Instant.now();
			synchronized (clients) {
				// System.out.println("Client updater client count: " + waitingClients.size());
				ListIterator<Client> it = waitingClients.listIterator();
				// System.out.println("Processing clients.");
				while (it.hasNext()) {
					// System.out.println("Processing client.");
					Client client = it.next();
					try {
						if (client.hasMessage()) {
							if (handleClient(client)) {
								it.remove();
							}
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
	}

	public void sendMessage(int messageID, String conversationID) {
		synchronized (clients) {
			ListIterator<Client> it = clients.listIterator();
			System.out.println("Sending message.");
			while (it.hasNext()) {
				// System.out.println("Processing client.");
				Client client = it.next();
				try {
					if (client.userID == null) {
						client.close();
					} else {
						System.out.println("Checking user with id:" + client.userID);
						ArrayList<Integer> conversations = UserDataAccesor
								.getUserConversations(Integer.valueOf(client.userID));
						System.out.println(conversations);

						if (conversations.contains(Integer.valueOf(conversationID).intValue())) {
							System.out.println("Sending message to client: " + client.userID);
							Hashtable<String, String> message = MessageDataAccesor.getData(messageID);
							Set<String> keys = message.keySet();
							JSONObject messageJSON = new JSONObject();
							for (String key : keys) {
								messageJSON.put(key, message.get(key));
							}
							JSONObject data = new JSONObject();
							data.put(MessagesTopLevelConstants.VALUE.value(), messageJSON);
							client.write((data.toString() + "\n").getBytes());
							System.out.println(data.toString() + "\n");
						}
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

	boolean handleClient(Client client) throws IOException {
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
			return false;
		}
		if (!LoginRequestTypes.SEND_LOGIN.value().equals(typeStr)) {
			System.out.println("Received request of incorrect type: " + typeStr);
			return false;
		}
		JSONObject value = null;
		try {
			value = messageJSON.getJSONObject(MessagesTopLevelConstants.VALUE.value());
		} catch (JSONException e) {
			System.out.println("Received request without value:");
			System.out.println(message);
			return false;
		}
		JSONObject responseJSON = Login.procesRequests(LoginRequestTypes.SEND_LOGIN, value);
		String response = responseJSON.toString() + "\n";
		System.out.print("\tResponse: " + response);
		client.write(response.getBytes());
		if (responseJSON.getJSONObject(MessagesTopLevelConstants.VALUE.value()).getBoolean("outcome")) {
			client.userID = responseJSON.getJSONObject(MessagesTopLevelConstants.VALUE.value()).getString("user_id");
			clients.add(client);
			return true;
		}
		client.close();
		return false;
	}
}

class ClientUpdaterAcceptor implements Runnable {
	ClientUpdater updater = null;

	public ClientUpdaterAcceptor(ClientUpdater updater) {
		this.updater = updater;
	}

	public void run() {
		try {
			ServerSocket server = new ServerSocket(8005);
			while (updater.running) {
				updater.addClient(server.accept());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}