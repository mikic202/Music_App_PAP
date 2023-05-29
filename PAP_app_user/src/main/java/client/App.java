package client;

import java.net.Socket;
import java.util.ArrayList;

import client.Chat.Chat;
import client.ServerConnector.ServerConnector;

public class App {
    public static void main(String[] args) throws Exception {

        System.out.println("Hello, World");
        ServerConnector connector = new ServerConnector(new Socket("144.91.114.89",
                8000));
        // ServerConnector connector = new ServerConnector(new Socket("144.91.114.89",
        // 8000));
        // Chat chat = new Chat(1, 2, connector);
        // ArrayList<String> users = new ArrayList<>();
        // users.add("mikic");
        // users.add("someone");
        // System.out.println(chat.getCurrentMessages());
    }
}
