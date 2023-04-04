package GUI.listeners;

// java Program to create a list and add itemListener to it
// (program to select your birthday using lists) .
import javax.swing.event.*;

import GUI.Conversations;

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

class solve extends JFrame implements ListSelectionListener {

    // frame
    static JFrame f;

    // lists
    static JList b, b1, b2;

    // label
    static JLabel l1;

    // main class
    public static void main(String[] args) {
        // create a new frame
        f = new JFrame("frame");

        // create a object
        solve s = new solve();

        // create a panel
        JPanel p = new JPanel();

        // create a new label
        JLabel l = new JLabel("select your birthday");
        l1 = new JLabel();

        // String array to store weekdays
        String month[] = { "January", "February", "March",
                "April", "May", "June", "July", "August",
                "September", "October", "November", "December" };

        // create a array for months and year
        String date[] = new String[31], year[] = new String[31];

        // add month number and year to list
        for (int i = 0; i < 31; i++) {
            date[i] = "" + (int) (i + 1);
            year[i] = "" + (int) (2018 - i);
        }

        // create lists
        b = new JList(date);
        b1 = new JList(month);
        b2 = new JList(year);

        // set a selected index
        b.setSelectedIndex(2);
        b1.setSelectedIndex(1);
        b2.setSelectedIndex(2);

        l1.setText(b.getSelectedValue() + " " + b1.getSelectedValue()
                + " " + b2.getSelectedValue());

        // add item listener
        b.addListSelectionListener(s);
        b1.addListSelectionListener(s);
        b2.addListSelectionListener(s);

        // add list to panel
        p.add(l);
        p.add(b);
        p.add(b1);
        p.add(b2);
        p.add(l1);

        f.add(p);

        // set the size of frame
        f.setSize(500, 600);

        f.show();
    }

    public void valueChanged(ListSelectionEvent e) {
        // set the text of the label to the selected value of lists
        l1.setText(b.getSelectedValue() + " " + b1.getSelectedValue()
                + " " + b2.getSelectedValue());

    }

}