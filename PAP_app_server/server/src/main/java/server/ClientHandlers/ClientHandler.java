package server.ClientHandlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import server.Chat.Chat;
import server.Chat.ChatRequestTypes;
import server.Login.Login;
import server.Login.LoginRequestTypes;
import server.files.UploadRequestProcessor;
import server.files.UploadRequestTypes;

import server.Music.MusicRequestHandler;
import server.Music.MusicRequestTypes;
import server.ServerConnectionConstants.ChatMessagesConstants;
import server.ServerConnectionConstants.MessagesTopLevelConstants;

public class ClientHandler implements Runnable {
	String MULTI_REQUEST = "multi request";
	List<Client> clients = new ArrayList<Client>();
	boolean running = true;
	final int THREAD_COUNT = 25;

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
		ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_COUNT);
		while (true) {
			Instant start = Instant.now();
			synchronized (clients) {
				ListIterator<Client> it = clients.listIterator();
				// System.out.println("Processing clients.");
				while (it.hasNext()) {
					// System.out.println("Processing client.");
					Client client = it.next();
					try {
						if (client.hasMessage()) {
							threadPool.submit(() -> {
								try {
									handleClient(client);
								} catch (IOException exception) {
									client.close();
								}
							});

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

	void handleClient(Client client) throws IOException {
		String message = client.getMessage();
		System.out.println("\tRequest: " + message);
		JSONObject messageJSON = null;
		try {
			messageJSON = new JSONObject(message);
		} catch (JSONException e) {
			System.out.println("Received request without type:");
			System.out.println(message);
			return;
		}
		handleMessage(messageJSON, client);
	}

	void handleMessage(JSONObject messageJSON, Client client) throws IOException {
		String typeStr;
		try {
			typeStr = messageJSON.getString(MessagesTopLevelConstants.TYPE.value());
		} catch (JSONException e) {
			System.out.println("Received request without type:");
			System.out.println(messageJSON);
			return;
		}
		JSONObject value = null;
		try {
			value = messageJSON.getJSONObject(MessagesTopLevelConstants.VALUE.value());
		} catch (JSONException e) {
			System.out.println("Received request without value:");
			System.out.println(messageJSON);
			return;
		}
		if (typeStr.equals(MULTI_REQUEST)) {
			for (int i = 0; i < messageJSON.getJSONArray(MessagesTopLevelConstants.VALUE.value())
					.length(); i++) {
				handleMessage(messageJSON, client);
			}
		}
		ChatRequestTypes chatType = null;
		String response = "";
		for (ChatRequestTypes t : ChatRequestTypes.values()) {
			if (t.value().equals(typeStr)) {
				chatType = t;
				response = Chat.procesRequests(chatType, value).toString() + "\n";
				System.out.print("\tResponse: " + response);
				client.write(response.getBytes());
				return;
			}
		}
		MusicRequestTypes music_type = null;
		for (MusicRequestTypes t : MusicRequestTypes.values()) {
			if (t.value().equals(typeStr)) {
				music_type = t;
				response = MusicRequestHandler.processRequests(music_type, value).toString() + "\n";
				System.out.print("\tResponse: " + response);
				client.write(response.getBytes());
				return;
			}
		}
		LoginRequestTypes login_type = null;
		for (LoginRequestTypes t : LoginRequestTypes.values()) {
			if (t.value().equals(typeStr)) {
				login_type = t;
				response = Login.procesRequests(login_type, value).toString() + "\n";
				System.out.print("\tResponse: " + response);
				client.write(response.getBytes());
				return;
			}
		}
		UploadRequestTypes upload_type = null;
		for (UploadRequestTypes t : UploadRequestTypes.values()) {
			if (t.value().equals(typeStr)) {
				upload_type = t;
				response = UploadRequestProcessor.procesRequests(upload_type, value).toString() + "\n";
				System.out.print("\tResponse: " + response);
				client.write(response.getBytes());
				return;
			}
		}
		System.out.println("Received request of incorrect type: " + typeStr);
	}
}
