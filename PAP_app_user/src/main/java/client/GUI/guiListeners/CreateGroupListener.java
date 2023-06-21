package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import client.Chat.Chat;
import client.ServerConnectionConstants.ChatMessagesConstants;

public class CreateGroupListener implements ActionListener {

    public CreateGroupListener(Chat chat, JTextField groupName, JTextArea membersToAdd, JList<String> chatList) {
        this.chat = chat;
        this.groupName = groupName;
        this.membersToAdd = membersToAdd;
        this.chatList = chatList;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (groupName.getText().trim().equals("")) {
            return;
        }
        new Thread(new CreateGroupGuiUpdater()).start();
    }

    class CreateGroupGuiUpdater implements Runnable {

        @Override
        public void run() {
            createConversation();
            updateConversationList();
        }

    }

    private void createConversation() {
        String name = groupName.getText();
        ArrayList<String> usernames = new ArrayList<>();
        String[] parsedUsernames = membersToAdd.getText().split(";");
        for (String username : parsedUsernames) {
            usernames.add(username);
        }
        usernames.add(chat.getCurrentUserInfo().getString(ChatMessagesConstants.USERNAME.value()));
        chat.createConversation(name, usernames);
    }

    private void updateConversationList() {
        chatList.removeAll();
        chat.updateStatus();
        var convNamesSet = chat.getConversationsNamesToIds().keySet();

        String[] convNames = new String[convNamesSet.size()];
        int i = 0;
        for (String name : convNamesSet) {
            convNames[i++] = name;
        }
        chatList.setModel(new javax.swing.AbstractListModel<String>() {

            public int getSize() {
                return convNames.length;
            }

            public String getElementAt(int i) {
                return convNames[i];
            }
        });
    }

    Chat chat;
    JTextField groupName;
    JTextArea membersToAdd;
    JList<String> chatList;
}
