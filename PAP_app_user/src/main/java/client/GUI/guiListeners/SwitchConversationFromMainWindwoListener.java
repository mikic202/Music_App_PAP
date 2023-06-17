package client.GUI.guiListeners;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONObject;

import client.Chat.Chat;

public class SwitchConversationFromMainWindwoListener implements ListSelectionListener {

    public SwitchConversationFromMainWindwoListener(Chat chat, Callable<Void> chatGuiUpdater) {
        this.chat = chat;
        this.chatGuiUpdater = chatGuiUpdater;
        newMessages = new ArrayList<>();
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (((JList) event.getSource()).getSelectedValue() == null) {
            return;
        }
        if (!event.getValueIsAdjusting()) {
            currentConversationName = ((JList) event.getSource()).getSelectedValue().toString();
            new Thread(new ConversationContentsUpdater()).start();
        }
    }

    private void updateChat() {
        try {
            chatGuiUpdater.call();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    class ConversationContentsUpdater implements Runnable {
        @Override
        public void run() {
            try {
                chat.switchConversations(currentConversationName);
                updateChat();
                MusicEventListener.onChatIdChange(chat.getCurrentChatId());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    String currentConversationName = "";
    Chat chat;
    Callable<Void> chatGuiUpdater;
    ArrayList<JSONObject> newMessages;

}
