package client.GUI.guiListeners;

import java.awt.Color;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
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
        // LeftChatPanel messagePanel = new LeftChatPanel();
        // messagePanel.chatText.setBackground(new java.awt.Color(0, 137, 255));
        // messagePanel.chatText.setForeground(Color.black);
        // messagePanel.chatText.setText(message.getString(ChatMessagesConstants.MESSAGE_TEXT.value()));
        // messagePanel.dateLabel.setText(message.getString("creation_date"));
        // var userInfo = chat.getCurrentUserInfo();
        // if (!userInfo.getString("profile_picture").equals("0")) {
        // String imageString = userInfo.getString("profile_picture");
        // messagePanel.avatarChat.setIcon((new
        // ImageIcon(convertStringArrayToImageBytes(imageString))));
        // }
        // messagePanel.nicknameLabel.setText(userInfo.getString(ChatMessagesConstants.USERNAME.value()));
        // this.messagesArea.add(messagePanel.chatBlock, "wrap");
        // this.messagesArea.repaint();
        // this.messagesArea.revalidate();
    }

    byte[] convertStringArrayToImageBytes(String stringImage) {
        stringImage = stringImage.replace("[", "");
        stringImage = stringImage.replace("]", "");
        List<String> byteListImage = Arrays.asList(stringImage.split(","));
        byte[] imageData = new byte[byteListImage.size()];

        for (int i = 0; i < byteListImage.size(); i++) {
            imageData[i] = Byte.parseByte(byteListImage.get(i));
        }
        return imageData;
    }

    Chat chat;
    JTextArea messageBox;
    JPanel messagesArea;
}
