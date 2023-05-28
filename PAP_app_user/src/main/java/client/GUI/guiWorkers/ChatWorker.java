package client.GUI.guiWorkers;

import java.net.Socket;

import javax.swing.SwingWorker;

import client.Chat.Chat;
import client.ServerConnector.ServerConnector;
import client.login_and_account_accessors.LoginAccessors;

public class ChatWorker extends SwingWorker<Boolean, Void> {

    public ChatWorker(Chat chat, char[] userPassword) {
        this.chat = chat;
        try {
            serverConnector = new ServerConnector(new Socket("144.91.114.89",
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
            System.out.println(data);
            System.out.println("status updated");
            if (data.getString("text").equals("")) {
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

}
