package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import client.GUI.LoginScreen;
import client.GUI.MainScreen;
import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.ServerConnector.ServerConnector;
import client.login_and_account_accessors.LoginAccessors;

public class LoggingListener implements ActionListener {

    public LoggingListener(LoginScreen loggingScreen, JTextField emailField, JPasswordField passwordField,
            ServerConnector serverConnector) {
        this.loggingScreen = loggingScreen;
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.serverConnector = serverConnector;
        loggingAccessors = new LoginAccessors(serverConnector);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (!emailField.getText().equals("") && !String.valueOf(passwordField.getPassword()).equals("")) {
            new Thread(new LoggingGuiUpdater()).start();
        }
    }

    class LoggingGuiUpdater implements Runnable {

        @Override
        public void run() {
            JSONObject response = loggingAccessors.sendUserLoginData(emailField.getText(),
                    passwordField.getPassword());
            if (response.getJSONObject(MessagesTopLevelConstants.VALUE.value()).getBoolean("outcome")) {
                response.getJSONObject(MessagesTopLevelConstants.VALUE.value()).put("email", emailField.getText());
                logIn(response.getJSONObject(MessagesTopLevelConstants.VALUE.value()), passwordField.getPassword());
                emailField.setText("");
                passwordField.setText("");
            } else {
                emailField.setText("");
                passwordField.setText("");
            }

        }

    }

    void logIn(JSONObject userInfo, char[] userPassword) {
        userInfo.remove("outcome");
        loggingScreen.dispose();
        MainScreen mainScreen = new MainScreen(serverConnector, userInfo, userPassword);
        mainScreen.setVisible(true);

    }

    LoginScreen loggingScreen;
    JTextField emailField;
    JPasswordField passwordField;
    ServerConnector serverConnector;
    LoginAccessors loggingAccessors;

}
