package client.GUI.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.GUI.LoginScreen;

public class LoggingActionListener implements ActionListener {

    LoginScreen logging_screen;

    public LoggingActionListener(LoginScreen logging_screen) {
        this.logging_screen = logging_screen;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        logging_screen.try_logging();
    }

}
