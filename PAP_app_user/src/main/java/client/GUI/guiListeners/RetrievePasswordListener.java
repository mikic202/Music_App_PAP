package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.JSONObject;

import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.ServerConnector.ServerConnector;
import client.login_and_account_accessors.LoginAccessors;

public class RetrievePasswordListener implements ActionListener {

    public RetrievePasswordListener(JLabel forgotPasswordLabel, JTextField emailField,
            ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
        this.emailField = emailField;
        this.forgotPasswordLabel = forgotPasswordLabel;
        loggingAccessors = new LoginAccessors(serverConnector);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (!emailField.getText().equals("")) {
            new Thread(new LoggingGuiUpdater()).start();
        }
    }

    class LoggingGuiUpdater implements Runnable {

        @Override
        public void run() {
            JSONObject response = loggingAccessors.sendRetrievePassword(emailField.getText());
            if (response.getJSONObject(MessagesTopLevelConstants.VALUE.value()).getBoolean("outcome")) {
                forgotPasswordLabel.setText("New Pasword sent to email");
            } else {
                emailField.setText("");
                forgotPasswordLabel.setText("Given Email does not have an account");
            }

        }

    }

    JTextField emailField;
    JLabel forgotPasswordLabel;
    ServerConnector serverConnector;
    LoginAccessors loggingAccessors;
}
