package client.GUI.guiListeners;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.json.JSONObject;
import client.Chat.Chat;
import client.GUI.ImageChatPanel;
import client.GUI.LeftChatPanel;
import client.ServerConnectionConstants.ChatMessagesConstants;
import client.helpers.ImageProcessor;

public class ChatContentsUpdater {
    static final int PROFILE_PICTURE_SIZE = 40;

    private static ImageIcon defaultIcon;
    static {
        try {
            Image defaultImage = ImageIO.read(new File("src/main/java/client/GUI/GuiResources/deaudlt.png"));
            Image scaledImage = defaultImage.getScaledInstance(PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE,
                    Image.SCALE_DEFAULT);
            defaultIcon = new ImageIcon(scaledImage);
        } catch (Exception e) {

        }
    };

    static public void updateChat(ArrayList<JSONObject> newMessages, Chat chat, JPanel messagesArea) {
        messagesArea.removeAll();
        for (int i = newMessages.size() - 1; i >= 0; i--) {
            var message = newMessages.get(i);
            if (message.getInt(ChatMessagesConstants.IS_IMAGE.value()) == 1) {
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
        if (message.getInt(ChatMessagesConstants.IS_IMAGE.value()) == 1) {
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
        messagesArea.repaint();
        messagesArea.revalidate();
    }

    static JPanel addImage(JSONObject message, Chat chat, JPanel messagesArea) {
        ImageChatPanel chatPanel = new ImageChatPanel();
        if (message.getInt(ChatMessagesConstants.MESSAGE_SENDER_ID.value()) == chat.userId()) {
            chatPanel.chatText.setBackground(new java.awt.Color(0, 137, 255));
        }
        String textImageString = message.getString(ChatMessagesConstants.MESSAGE_TEXT.value());
        try {
            BufferedImage defaultImage = ImageProcessor.convertStringArrayToImage(textImageString);
            Image scaledImage = defaultImage.getScaledInstance(300,
                    defaultImage.getHeight() * (300 / defaultImage.getWidth()), Image.SCALE_DEFAULT);
            chatPanel.chatText.setIcon((new ImageIcon(scaledImage)));
        } catch (Exception e) {
            System.out.println(e);
        }
        chatPanel.dateLabel.setText(message.getString("creation_date"));
        var userInfo = chat.getUserInformation(message.getInt(ChatMessagesConstants.MESSAGE_SENDER_ID.value()));
        if (!userInfo.getString("profile_picture").equals("0")) {
            try {
                String imageString = userInfo.getString("profile_picture");
                BufferedImage defaultImage = ImageProcessor.convertStringArrayToImage(imageString);
                Image scaledImage = defaultImage.getScaledInstance(PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE,
                        Image.SCALE_DEFAULT);
                chatPanel.avatarChat.setIcon((new ImageIcon(scaledImage)));
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                chatPanel.avatarChat.setIcon(defaultIcon);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        chatPanel.nicknameLabel.setText(userInfo.getString(ChatMessagesConstants.USERNAME.value()));
        return chatPanel.chatBlock;
    }

    static JPanel addTextMessage(JSONObject message, Chat chat, JPanel messagesArea) {
        LeftChatPanel chatPanel = new LeftChatPanel();
        if (message.getInt(ChatMessagesConstants.MESSAGE_SENDER_ID.value()) == chat.userId()) {
            chatPanel.chatText.setBackground(new java.awt.Color(0, 137, 255));
        }
        chatPanel.chatText.setText(message.getString(ChatMessagesConstants.MESSAGE_TEXT.value()));
        chatPanel.dateLabel.setText(message.getString("creation_date"));
        JSONObject userInfo = chat.getUserInformation(message.getInt(ChatMessagesConstants.MESSAGE_SENDER_ID.value()));
        if (!userInfo.getString("profile_picture").equals("0")) {
            String imageString = userInfo.getString("profile_picture");
            chatPanel.avatarChat.setIcon((new ImageIcon(ImageProcessor.convertStringArrayToImageBytes(imageString))));
        } else {
            try {
                chatPanel.avatarChat.setIcon(defaultIcon);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        chatPanel.nicknameLabel.setText(userInfo.getString(ChatMessagesConstants.USERNAME.value()));
        return chatPanel.chatBlock;
    }

}
