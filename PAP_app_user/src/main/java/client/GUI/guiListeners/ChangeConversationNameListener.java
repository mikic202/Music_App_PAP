package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;
import javax.swing.JTextField;
import client.Chat.Chat;

public class ChangeConversationNameListener implements ActionListener {

    public ChangeConversationNameListener(JTextField neConversationNameField, Chat chat,
            Callable<Void> chatGuiUpdater) {
        this.neConversationNameField = neConversationNameField;
        this.chatGuiUpdater = chatGuiUpdater;
        this.chat = chat;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (!neConversationNameField.getText().equals("")) {
            new Thread(new ChangeConversationNameGuiUpdater()).start();
        }
    }

    class ChangeConversationNameGuiUpdater implements Runnable {

        @Override
        public void run() {
            String newConversationName = neConversationNameField.getText();
            chat.changeCurrentConversationName(newConversationName);
            try {
                chatGuiUpdater.call();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    private JTextField neConversationNameField;
    Callable<Void> chatGuiUpdater;
    private Chat chat;

}
