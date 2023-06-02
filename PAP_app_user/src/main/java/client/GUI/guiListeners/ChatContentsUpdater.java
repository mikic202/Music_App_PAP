package client.GUI.guiListeners;

import java.util.List;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.json.JSONObject;

import client.Chat.Chat;
import client.GUI.ImageChatPanel;
import client.GUI.LeftChatPanel;
import client.ServerConnectionConstants.ChatMessagesConstants;

public class ChatContentsUpdater {
    static final int PROFILE_PICTURE_SIZE = 40;

    private static Image defaultImage;
    static {
        try {
            defaultImage = ImageIO.read(new File("src\\main\\java\\client\\GUI\\deaudlt.png"));
        } catch (Exception e) {

        }
    };

    static public void updateChat(ArrayList<JSONObject> newMessages, Chat chat, JPanel messagesArea) {
        messagesArea.removeAll();
        for (int i = newMessages.size() - 1; i >= 0; i--) {
            var message = newMessages.get(i);
            if (message.getInt("is_image") == 1) {
                messagesArea.add(addImage(message, chat, messagesArea), "wrap");
            } else {
                messagesArea.add(addTextMessage(message, chat, messagesArea), "wrap");
            }
        }
        messagesArea.repaint();
        messagesArea.revalidate();
    }

    static public void addMessageToConversation(JSONObject message, Chat chat, JPanel messagesArea,
            boolean atTheFront) {
        if (message.getInt("is_image") == 1) {
            var chatBlock = addImage(message, chat, messagesArea);
            if (atTheFront
                    && chat.getCurrentChatId() == message.getInt(ChatMessagesConstants.CONVERSATION_ID.value())) {
                messagesArea.add(chatBlock, "wrap", 0);
            } else if (chat.getCurrentChatId() == message.getInt(ChatMessagesConstants.CONVERSATION_ID.value())) {
                messagesArea.add(chatBlock, "wrap");
            }
        } else {
            var chatBlock = addTextMessage(message, chat, messagesArea);
            if (atTheFront
                    && chat.getCurrentChatId() == message.getInt(ChatMessagesConstants.CONVERSATION_ID.value())) {
                messagesArea.add(chatBlock, "wrap", 0);
            } else if (chat.getCurrentChatId() == message.getInt(ChatMessagesConstants.CONVERSATION_ID.value())) {
                messagesArea.add(chatBlock, "wrap");
            }
        }
    }

    static JPanel addImage(JSONObject message, Chat chat, JPanel messagesArea) {
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
        } else {
            try {
                Image scaledImage = defaultImage.getScaledInstance(PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE,
                        Image.SCALE_DEFAULT);
                chatPanel.avatarChat.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        chatPanel.nicknameLabel.setText(userInfo.getString(ChatMessagesConstants.USERNAME.value()));
        return chatPanel.chatBlock;
    }

    static JPanel addTextMessage(JSONObject message, Chat chat, JPanel messagesArea) {
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
        } else {
            try {
                Image defaultImage = ImageIO.read(new File("src\\main\\java\\client\\GUI\\deaudlt.png"));
                Image scaledImage = defaultImage.getScaledInstance(PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE,
                        Image.SCALE_DEFAULT);
                chatPanel.avatarChat.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        chatPanel.nicknameLabel.setText(userInfo.getString(ChatMessagesConstants.USERNAME.value()));
        return chatPanel.chatBlock;
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
