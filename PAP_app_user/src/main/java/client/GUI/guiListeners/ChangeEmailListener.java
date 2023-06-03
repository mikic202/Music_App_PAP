package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import client.Chat.Chat;
import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.ServerConnector.ServerConnector;
import client.login_and_account_accessors.AccountChangeRequestAccessors;
import client.ServerConnectionConstants.ChatMessagesConstants;

public class ChangeEmailListener implements ActionListener {

    public ChangeEmailListener(JTextField newEmailField,
            ServerConnector serverConnector, Chat chat, JLabel emailLabel) {
        this.newEmailField = newEmailField;
        this.serverConnector = serverConnector;
        this.chat = chat;
        accountAccesor = new AccountChangeRequestAccessors(serverConnector);
        this.emailLabel = emailLabel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (!newEmailField.getText().equals("")) {
            new Thread(new ChangeEmailGuiUpdater()).start();
        }
    }

    class ChangeEmailGuiUpdater implements Runnable {

        @Override
        public void run() {
            String newEmail = newEmailField.getText();
            JSONObject response = accountAccesor.sendUserAccountEmailData(newEmail,
                    chat.getCurrentUserInfo().getInt(ChatMessagesConstants.USER_ID.value()));
            if (response.getJSONObject(MessagesTopLevelConstants.VALUE.value()).getBoolean("outcome")) {
                emailLabel.setText(newEmail);
            }
        }

    }

    private JTextField newEmailField;
    private ServerConnector serverConnector;
    private AccountChangeRequestAccessors accountAccesor;
    private Chat chat;
    private JLabel emailLabel;

}
