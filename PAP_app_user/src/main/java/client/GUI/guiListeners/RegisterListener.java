package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.GUI.InformationWindow;
import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.ServerConnector.ServerConnector;
import client.login_and_account_accessors.LoginAccessors;

public class RegisterListener implements ActionListener {

    public RegisterListener(JTextField emailField, JTextField usernameField, JPasswordField passwordField,
            JPasswordField repeatPasswordField,
            ServerConnector serverConnector, JLabel registerLabel) {
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.repaetPasswordField = repeatPasswordField;
        this.usernameField = usernameField;
        loggingAccessors = new LoginAccessors(serverConnector);
        this.registerLabel = registerLabel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (!emailField.getText().equals("") && !String.valueOf(passwordField.getPassword()).equals("")
                && !String.valueOf(repaetPasswordField.getPassword()).equals("")) {
            new Thread(new RegistergGuiUpdater()).start();
        }
    }

    class RegistergGuiUpdater implements Runnable {

        @Override
        public void run() {
            if (!String.valueOf(passwordField.getPassword())
                    .equals(String.valueOf(repaetPasswordField.getPassword()))) {
                passwordField.setText("");
                repaetPasswordField.setText("");
            } else {
                if (sendRegisterRequest()) {
                    new InformationWindow("User Registered!").setVisible(true);
                }
            }

        }

    }

    boolean sendRegisterRequest() {
        var response = loggingAccessors.sendUserRegisterData(emailField.getText(), usernameField.getText(),
                passwordField.getPassword(),
                repaetPasswordField.getPassword());
        return response.getBoolean(MessagesTopLevelConstants.OUTCOME.value());
    }

    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repaetPasswordField;;
    private LoginAccessors loggingAccessors;
    private JLabel registerLabel;

}
