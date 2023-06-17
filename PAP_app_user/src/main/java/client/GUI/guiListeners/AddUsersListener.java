package client.GUI.guiListeners;

import java.awt.Color;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONObject;

import client.Chat.Chat;
import client.GUI.LeftChatPanel;
import client.ServerConnectionConstants.ChatMessagesConstants;

public class AddUsersListener implements ActionListener {

    public AddUsersListener(Chat chat, JTextArea membersToAdd) {
        this.chat = chat;
        this.membersToAdd = membersToAdd;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (membersToAdd.getText().trim().equals("")) {
            return;
        }
        new Thread(new CreateGroupGuiUpdater()).start();
    }

    class CreateGroupGuiUpdater implements Runnable {

        @Override
        public void run() {
            addToConversation();
        }

    }

    private void addToConversation() {
        String[] parsed_usernames = membersToAdd.getText().split(";");
        ArrayList<String> usernames = new ArrayList<>();
        for (String username : parsed_usernames) {
            usernames.add(username);
        }
        usernames.add(chat.getCurrentUserInfo().getString(ChatMessagesConstants.USERNAME.value()));
        membersToAdd.setText("");
    }

    Chat chat;
    JTextArea membersToAdd;
}
