package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.GUI.LoginScreen;

public class LoggingListener implements ActionListener {

    public LoggingListener(LoginScreen loggingScreen, JTextField emailField, JPasswordField passwordField) {

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        new Thread(new LoggingGuiUpdater()).run();
    }

    class LoggingGuiUpdater implements Runnable {

        @Override
        public void run() {

        }

    }

}
