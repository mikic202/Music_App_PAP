package client.GUI.guiListeners;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.json.JSONObject;
import client.Chat.Chat;

public class SwitchConversationListener implements ListSelectionListener {

    public SwitchConversationListener(Chat chat, JPanel messagesArea, Callable<Void> chatGuiUpdater) {
        this.chat = chat;
        this.messagesArea = messagesArea;
        this.chatGuiUpdater = chatGuiUpdater;
        newMessages = new ArrayList<>();
        updateChatPaneThread = new ConversationMessagesUpdater();
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

    private void updateChat(ArrayList<JSONObject> newMessages) {
        updateChatPaneThread.stopUpdating();
        updateChatPaneThread = new ConversationMessagesUpdater();
        this.newMessages = newMessages;
        try {
            chatGuiUpdater.call();
        } catch (Exception e) {
            System.out.println(e);
        }
        updateChatPaneThread.start();
    }

    class ConversationContentsUpdater implements Runnable {
        @Override
        public void run() {
            try {
                ArrayList<JSONObject> newMessagesAdded = chat
                        .switchConversations(currentConversationName);
                updateChat(newMessagesAdded);
                MusicEventListener.onChatIdChange(chat.getCurrentChatId());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    class ConversationMessagesUpdater extends Thread {
        @Override
        public void run() {
            messagesArea.removeAll();
            needsToRun = true;
            for (int i = newMessages.size() - 1; i >= 0; i--) {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (!needsToRun) {
                    break;
                } else if (needsToRun) {
                    ChatContentsUpdater.addMessageToConversation(newMessages.get(i), chat, messagesArea, false);
                }
            }
            messagesArea.repaint();
            messagesArea.revalidate();
        }

        public void stopUpdating() {
            needsToRun = false;
        }

        private Boolean needsToRun = true;
    }

    String currentConversationName = "";
    Chat chat;
    JPanel messagesArea;
    Callable<Void> chatGuiUpdater;
    private ConversationMessagesUpdater updateChatPaneThread;
    ArrayList<JSONObject> newMessages;

}
