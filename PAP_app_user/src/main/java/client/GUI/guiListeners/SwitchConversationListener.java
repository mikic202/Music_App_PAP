package client.GUI.guiListeners;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public SwitchConversationListener(Chat chat, JPanel messagesArea) {
        this.chat = chat;
        this.messagesArea = messagesArea;
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
            // JLabel username = new JLabel();
            // username.setText(users_in_conv.get(message.getInt("sender")).getString("username"));
            // JLabel time = new JLabel();
            // time.setText(message.getString("send_date"));
            // this.messagesArea
            // .add(username);
            // this.messagesArea.add(time);
            if (message.getInt("is_image") == 1) {
                addImage(message);
            } else {
                addTextMessage(message);
            }
        }

    }

    void addImage(JSONObject message) {
        ImageChatPanel chatPanel = new ImageChatPanel();
        if (message.getInt("sender_id") == chat.userId()) {
            chatPanel.jTextArea1.setBackground(new java.awt.Color(0, 137, 255));
        }
        String imageString = message.getString("text");
        imageString = imageString.replace("[", "");
        imageString = imageString.replace("]", "");
        List<String> byteListImage = Arrays.asList(imageString.split(","));
        byte[] imageData = new byte[byteListImage.size()];

        for (int i = 0; i < byteListImage.size(); i++) {
            imageData[i] = Byte.parseByte(byteListImage.get(i));
        }

        chatPanel.jTextArea1.setIcon((new ImageIcon(imageData)));
        this.messagesArea.add(chatPanel.jTextArea1, "wrap");
        this.messagesArea.repaint();
        this.messagesArea.revalidate();
    }

    void addTextMessage(JSONObject message) {
        LeftChatPanel chatPanel = new LeftChatPanel();
        if (message.getInt("sender_id") == chat.userId()) {
            chatPanel.jTextArea1.setBackground(new java.awt.Color(0, 137, 255));
        }
        chatPanel.jTextArea1.setText(message.getString("text"));
        this.messagesArea.add(chatPanel.jTextArea1, "wrap");
        this.messagesArea.repaint();
        this.messagesArea.revalidate();
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

    String currentConversationName = "";
    Chat chat;
    JPanel messagesArea;

}
