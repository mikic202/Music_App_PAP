package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.swing.JTextArea;
import client.Chat.Chat;
import client.ServerConnectionConstants.MessagesTopLevelConstants;

public class AddUsersListener implements ActionListener {

    public AddUsersListener(Chat chat, JTextArea membersToAdd, Callable<Void> chatGuiUpdater) {
        this.chat = chat;
        this.membersToAdd = membersToAdd;
        this.chatGuiUpdater = chatGuiUpdater;
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
        String[] parsedUsernames = membersToAdd.getText().split(";");
        ArrayList<String> usernames = new ArrayList<>();
        for (String username : parsedUsernames) {
            usernames.add(username);
        }
        var response = chat.addUsersToCurrentConversation(usernames);
        if (response.getBoolean(MessagesTopLevelConstants.OUTCOME.value())) {
            try {
                chatGuiUpdater.call();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        membersToAdd.setText("");
    }

    Chat chat;
    JTextArea membersToAdd;
    Callable<Void> chatGuiUpdater;
}
