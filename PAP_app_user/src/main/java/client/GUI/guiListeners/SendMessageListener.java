package client.GUI.guiListeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import client.Chat.Chat;
import client.GUI.LeftChatPanel;

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
        new Thread(new SendMessageGuiUpdater()).run();
        if (messageBox.getText().trim().equals("")) {
            return;
        }
    }

    class SendMessageGuiUpdater implements Runnable {

        @Override
        public void run() {
            String message = messageBox.getText().trim();
            LeftChatPanel messagePanel = new LeftChatPanel();
            messagePanel.jTextArea1.setForeground(Color.black);
            messagePanel.jTextArea1.setText(message);
            messagesArea.add(messagePanel.jTextArea1, "wrap");
            messagesArea.repaint();
            messagesArea.revalidate();
            messageBox.setText("");
        }

    }

    Chat chat;
    JTextArea messageBox;
    JPanel messagesArea;
}
