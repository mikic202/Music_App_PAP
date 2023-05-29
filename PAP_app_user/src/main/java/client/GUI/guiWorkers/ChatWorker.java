package client.GUI.guiWorkers;

import java.net.Socket;
import java.util.concurrent.Callable;

import javax.swing.SwingWorker;

import client.Chat.Chat;
import client.ServerConnector.ServerConnector;
import client.login_and_account_accessors.LoginAccessors;

public class ChatWorker extends SwingWorker<Boolean, Void> {

    public ChatWorker(Chat chat, char[] userPassword, Callable<Void> updateChatContents) {
        this.chat = chat;
        this.updateChatContents = updateChatContents;
        try {
            serverConnector = new ServerConnector(new Socket("localhost",
                    8005));
        } catch (Exception e) {
            System.out.println(e);
        }
        isConnected = initConnection(userPassword);

    }

    @Override
    protected Boolean doInBackground() throws Exception {
        while (true) {
            var data = serverConnector.waitForData();
            chat.updateStatus();
            updateChatContents.call();
            System.out.println(data);
            System.out.println("status updated");
            if (data.getJSONObject("value").keySet().contains("outcome")
                    && !data.getJSONObject("value").getBoolean("outcome")) {
                return false;
            }
        }
    }

    boolean initConnection(char[] userPassword) {
        LoginAccessors loggingAccesor = new LoginAccessors(serverConnector);
        var response = loggingAccesor.sendUserLoginData(chat.getCurrentUserInfo().getString("email"), userPassword);
        return response.getJSONObject("value").getBoolean("outcome");
    }

    private Chat chat;
    private ServerConnector serverConnector;
    private boolean isConnected = false;
    Callable<Void> updateChatContents;

}
