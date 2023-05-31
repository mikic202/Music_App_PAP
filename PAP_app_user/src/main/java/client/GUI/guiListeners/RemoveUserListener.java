package client.GUI.guiListeners;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.Callable;
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

public class RemoveUserListener implements ActionListener {

    public RemoveUserListener(Chat chat, JList<String> membersInConvList, Callable<Void> guiUpdater) {
        this.chat = chat;
        this.membersInConvList = membersInConvList;
        this.guiUpdater = guiUpdater;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (membersInConvList.getSelectedValue() == null) {
            return;
        }
        new Thread(new RemoveUserGuiUpdater()).start();
    }

    class RemoveUserGuiUpdater implements Runnable {

        @Override
        public void run() {
            removeUser();
        }

    }

    private void removeUser() {
        boolean outcome = chat.removeUserFromCurrentConversation(membersInConvList.getSelectedValue());
        if (outcome) {
            try {
                guiUpdater.call();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    Chat chat;
    JList<String> membersInConvList;
    Callable<Void> guiUpdater;
}
