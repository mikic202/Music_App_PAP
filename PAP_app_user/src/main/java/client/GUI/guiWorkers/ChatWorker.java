package client.GUI.guiWorkers;

import java.net.Socket;
import java.util.concurrent.Callable;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.json.JSONObject;

import client.Chat.Chat;
import client.GUI.guiListeners.ChatContentsUpdater;
import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.ServerConnector.ServerConnector;
import client.login_and_account_accessors.LoginAccessors;

public class ChatWorker extends SwingWorker<Boolean, Void> {

    public ChatWorker(Chat chat, char[] userPassword, Callable<Void> chatInfoUpdater, JPanel messagesArea) {
        this.messagesArea = messagesArea;
        this.chat = chat;
        this.chatInfoUpdater = chatInfoUpdater;
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
            if (!data.getJSONObject(MessagesTopLevelConstants.VALUE.value()).keySet()
                    .contains(MessagesTopLevelConstants.OUTCOME.value())) {
                chat.addExternalMessage(data.getJSONObject(MessagesTopLevelConstants.VALUE.value()));
                chat.updateStatus();
                if (!addMesageToCurrentConversationView(data.getJSONObject(MessagesTopLevelConstants.VALUE.value()))) {
                    chatInfoUpdater.call();
                }
            }
            if (data.getJSONObject(MessagesTopLevelConstants.VALUE.value()).keySet()
                    .contains(MessagesTopLevelConstants.OUTCOME.value())
                    && !data.getJSONObject(MessagesTopLevelConstants.VALUE.value())
                            .getBoolean(MessagesTopLevelConstants.OUTCOME.value())) {
                return false;
            }
        }
    }

    boolean initConnection(char[] userPassword) {
        LoginAccessors loggingAccesor = new LoginAccessors(serverConnector);
        var response = loggingAccesor.sendUserLoginData(
                chat.getCurrentUserInfo().getString(ChatMessagesConstants.EMAIL.value()), userPassword);
        return response.getJSONObject(MessagesTopLevelConstants.VALUE.value())
                .getBoolean(MessagesTopLevelConstants.OUTCOME.value());
    }

    private Boolean addMesageToCurrentConversationView(JSONObject message) {
        if (message.getInt(ChatMessagesConstants.CONVERSATION_ID.value()) == chat.getCurrentChatId()) {
            ChatContentsUpdater.addMessageToConversation(message, chat, messagesArea, true);
            return true;
        }
        return false;
    }

    private Chat chat;
    private ServerConnector serverConnector;
    private boolean isConnected = false;
    Callable<Void> chatInfoUpdater;
    JPanel messagesArea;

}
