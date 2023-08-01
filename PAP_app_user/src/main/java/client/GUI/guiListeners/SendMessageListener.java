package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import client.Chat.Chat;

public class SendMessageListener implements ActionListener {

    public SendMessageListener(JTextArea messageBox, JPanel messagesArea) {
        this.messageBox = messageBox;
        this.messagesArea = messagesArea;
    }

    public SendMessageListener(Chat chat, JTextArea messageBox, JPanel messagesArea) {
        this.chat = chat;
        this.messageBox = messageBox;
        this.messagesArea = messagesArea;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (messageBox.getText().trim().equals("")) {
            return;
        }
        new Thread(new SendMessageGuiUpdater()).start();
    }

    class SendMessageGuiUpdater implements Runnable {

        @Override
        public void run() {
            chat.sendMessage(messageBox.getText().trim());
            messageBox.setText("");
        }

    }

    Chat chat;
    JTextArea messageBox;
    JPanel messagesArea;
}
