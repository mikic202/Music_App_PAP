package client.GUI.guiListeners;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONObject;

import client.Chat.Chat;
import client.GUI.ImageChatPanel;
import client.GUI.LeftChatPanel;

public class SwitchConversationListener implements ListSelectionListener {

    public SwitchConversationListener(Chat chat, JPanel messagesArea, Callable<Void> chatGuiUpdater) {
        this.chat = chat;
        this.messagesArea = messagesArea;
        this.chatGuiUpdater = chatGuiUpdater;
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {

        if (!event.getValueIsAdjusting()) {
            currentConversationName = ((JList) event.getSource()).getSelectedValue().toString();
            new Thread(new ConversationMessagesUpdater()).start();

        }

    }

    private void updateChat(ArrayList<JSONObject> newMessages) {
        ChatContentsUpdater.updateChat(newMessages, chat, messagesArea);
        try {
            chatGuiUpdater.call();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    class ConversationMessagesUpdater implements Runnable {

        @Override
        public void run() {
            try {
                ArrayList<JSONObject> newMessages = chat
                        .switchConversations(currentConversationName);
                updateChat(newMessages);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    interface chatGuiUpdater {
        void updateChatUi();
    }

    String currentConversationName = "";
    Chat chat;
    JPanel messagesArea;
    Callable<Void> chatGuiUpdater;

}
