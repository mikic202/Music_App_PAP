package client.GUI.guiListeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.json.JSONObject;

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
        if (messageBox.getText().trim().equals("")) {
            return;
        }
        new Thread(new SendMessageGuiUpdater()).start();
    }

    class SendMessageGuiUpdater implements Runnable {

        @Override
        public void run() {
            addMessageToMessageArea(chat.sendMessage(messageBox.getText().trim()));
            messageBox.setText("");
        }

    }

    private void addMessageToMessageArea(JSONObject message) {
        LeftChatPanel messagePanel = new LeftChatPanel();
        messagePanel.jTextArea1.setForeground(Color.black);
        messagePanel.jTextArea1.setText(message.getString("text"));
        messagesArea.add(messagePanel.jTextArea1, "wrap");
        messagesArea.repaint();
        messagesArea.revalidate();
    }

    Chat chat;
    JTextArea messageBox;
    JPanel messagesArea;
}
