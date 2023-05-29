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
        messagesArea.removeAll();
        for (JSONObject message : newMessages) {
            if (message.getInt("is_image") == 1) {
                addImage(message);
            } else {
                addTextMessage(message);
            }
        }
        try {
            chatGuiUpdater.call();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    void addImage(JSONObject message) {
        ImageChatPanel chatPanel = new ImageChatPanel();
        if (message.getInt("sender_id") == chat.userId()) {
            chatPanel.chatText.setBackground(new java.awt.Color(0, 137, 255));
        }
        String textImageString = message.getString("text");
        chatPanel.chatText.setIcon((new ImageIcon(convertStringArrayToImageBytes(textImageString))));
        // TODO scale images
        chatPanel.dateLabel.setText(message.getString("creation_date"));
        var userInfo = chat.getUserInformation(message.getInt("sender_id"));
        if (!userInfo.getString("profile_picture").equals("0")) {
            String imageString = userInfo.getString("profile_picture");
            chatPanel.avatarChat.setIcon((new ImageIcon(convertStringArrayToImageBytes(imageString))));
        }
        chatPanel.nicknameLabel.setText(userInfo.getString("username"));
        this.messagesArea.add(chatPanel.chatBlock, "wrap");
        this.messagesArea.repaint();
        this.messagesArea.revalidate();
    }

    void addTextMessage(JSONObject message) {
        LeftChatPanel chatPanel = new LeftChatPanel();
        // leftChatPanel.chatText.setText(message);
        // leftChatPanel.chatText.setForeground(Color.black);
        // // this.chatPanel.add(leftChatPanel.avatarChat);
        // this.chatPanel.add(leftChatPanel.chatBlock, "wrap");
        // this.chatPanel.repaint();
        // this.chatPanel.revalidate();
        // this.textArea.setText("");
        if (message.getInt("sender_id") == chat.userId()) {
            chatPanel.chatText.setBackground(new java.awt.Color(0, 137, 255));
        }
        chatPanel.chatText.setText(message.getString("text"));
        chatPanel.dateLabel.setText(message.getString("creation_date"));
        var userInfo = chat.getUserInformation(message.getInt("sender_id"));
        if (!userInfo.getString("profile_picture").equals("0")) {
            String imageString = userInfo.getString("profile_picture");
            chatPanel.avatarChat.setIcon((new ImageIcon(convertStringArrayToImageBytes(imageString))));
        }
        chatPanel.nicknameLabel.setText(userInfo.getString("username"));
        this.messagesArea.add(chatPanel.chatBlock, "wrap");
        this.messagesArea.repaint();
        this.messagesArea.revalidate();
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
