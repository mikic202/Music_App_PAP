package client.GUI.listeners;

// java Program to create a list and add itemListener to it
// (program to select your birthday using lists) .
import javax.swing.event.*;

import client.GUI.Conversations;

import java.awt.*;
import javax.swing.*;

public class ChatListener implements ListSelectionListener {
    Conversations conversation;

    public ChatListener(Conversations above) {
        conversation = above;
    }

    public void valueChanged(ListSelectionEvent event) {
        // set the text of the label to the selected value of lists
        if (!event.getValueIsAdjusting()) {
            JList source = (JList) event.getSource();
            String selected = source.getSelectedValue().toString();
            conversation.change_messages(selected);
        }

    }
}