package client.GUI.guiListeners;

import java.util.List;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.json.JSONObject;

import client.Chat.Chat;
import client.GUI.ImageChatPanel;
import client.GUI.LeftChatPanel;

public class ChatContentsUpdater {
    static public void updateChat(ArrayList<JSONObject> newMessages, Chat chat, JPanel messagesArea) {
        messagesArea.removeAll();
        for (JSONObject message : newMessages) {
            if (message.getInt("is_image") == 1) {
                addImage(message, chat, messagesArea);
            } else {
                addTextMessage(message, chat, messagesArea);
            }
        }
        messagesArea.repaint();
        messagesArea.revalidate();
    }

    static void addImage(JSONObject message, Chat chat, JPanel messagesArea) {
        ImageChatPanel chatPanel = new ImageChatPanel();
        if (message.getInt("sender_id") == chat.userId()) {
            chatPanel.chatText.setBackground(new java.awt.Color(0, 137, 255));
        }
        String textImageString = message.getString("text");
        try {
            BufferedImage defaultImage = ImageIO
                    .read(new ByteArrayInputStream((convertStringArrayToImageBytes(textImageString))));
            Image scaledImage = defaultImage.getScaledInstance(300,
                    defaultImage.getHeight() * (300 / defaultImage.getWidth()), Image.SCALE_DEFAULT);
            chatPanel.chatText.setIcon((new ImageIcon(scaledImage)));
        } catch (Exception e) {
            System.out.println(e);
        }

        // TODO scale images
        chatPanel.dateLabel.setText(message.getString("creation_date"));
        var userInfo = chat.getUserInformation(message.getInt("sender_id"));
        if (!userInfo.getString("profile_picture").equals("0")) {
            String imageString = userInfo.getString("profile_picture");
            chatPanel.avatarChat.setIcon((new ImageIcon(convertStringArrayToImageBytes(imageString))));
        }
        chatPanel.nicknameLabel.setText(userInfo.getString("username"));
        messagesArea.add(chatPanel.chatBlock, "wrap");
    }

    static void addTextMessage(JSONObject message, Chat chat, JPanel messagesArea) {
        LeftChatPanel chatPanel = new LeftChatPanel();
        if (message.getInt("sender_id") == chat.userId()) {
            chatPanel.chatText.setBackground(new java.awt.Color(0, 137, 255));
        }
        chatPanel.chatText.setText(message.getString("text"));
        chatPanel.dateLabel.setText(message.getString("creation_date"));
        JSONObject userInfo = chat.getUserInformation(message.getInt("sender_id"));
        if (!userInfo.getString("profile_picture").equals("0")) {
            String imageString = userInfo.getString("profile_picture");
            chatPanel.avatarChat.setIcon((new ImageIcon(convertStringArrayToImageBytes(imageString))));
        }
        chatPanel.nicknameLabel.setText(userInfo.getString("username"));
        messagesArea.add(chatPanel.chatBlock, "wrap");
    }

    static byte[] convertStringArrayToImageBytes(String stringImage) {
        stringImage = stringImage.replace("[", "");
        stringImage = stringImage.replace("]", "");
        List<String> byteListImage = Arrays.asList(stringImage.split(","));
        byte[] imageData = new byte[byteListImage.size()];

        for (int i = 0; i < byteListImage.size(); i++) {
            imageData[i] = Byte.parseByte(byteListImage.get(i));
        }
        return imageData;
    }

}
