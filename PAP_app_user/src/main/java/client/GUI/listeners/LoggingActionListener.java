package client.GUI.listeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.GUI.LoginScreen;

public class LoggingActionListener implements ListSelectionListener {

    LoginScreen logingb_screen;

    public LoggingActionListener(LoginScreen logingb_screen) {
        this.logingb_screen = logingb_screen;
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'valueChanged'");
    }

}
