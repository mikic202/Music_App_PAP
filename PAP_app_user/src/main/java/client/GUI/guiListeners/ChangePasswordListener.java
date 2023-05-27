package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import client.Chat.Chat;
import client.ServerConnector.ServerConnector;
import client.login_and_account_accessors.AccountChangeRequestAccessors;

public class ChangePasswordListener implements ActionListener {

    public ChangePasswordListener(JPasswordField oldPasswordField, JPasswordField newPasswordField,
            JPasswordField repeatNewPasswordField,
            ServerConnector serverConnector, Chat chat, JLabel succesfulPasswordChangeLable) {
        this.oldPasswordField = oldPasswordField;
        this.newPasswordField = newPasswordField;
        this.repeatNewPasswordField = repeatNewPasswordField;
        this.serverConnector = serverConnector;
        this.chat = chat;
        accountAccesor = new AccountChangeRequestAccessors(serverConnector);
        this.succesfulPasswordChangeLable = succesfulPasswordChangeLable;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (!String.valueOf(oldPasswordField.getPassword())
                .equals("")
                && !String.valueOf(newPasswordField.getPassword())
                        .equals("")
                && !String.valueOf(repeatNewPasswordField.getPassword())
                        .equals("")) {
            new Thread(new ChangePasswordGuiUpdater()).start();
        }
    }

    class ChangePasswordGuiUpdater implements Runnable {

        @Override
        public void run() {
            if (String.valueOf(newPasswordField.getPassword())
                    .equals(String.valueOf(repeatNewPasswordField.getPassword()))) {
                JSONObject response = accountAccesor.sendUserAccountNewPasswordData(newPasswordField.getPassword(),
                        oldPasswordField.getPassword(),
                        chat.getCurrentUserInfo().getInt("user_id"));
                if (response.getJSONObject("value").getBoolean("outcome")) {
                    succesfulPasswordChangeLable.setText("Password Changed succesully");
                } else {
                    succesfulPasswordChangeLable.setText("Couldn't change the password");
                }
            }
        }

    }

    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField repeatNewPasswordField;
    private ServerConnector serverConnector;
    private AccountChangeRequestAccessors accountAccesor;
    private Chat chat;
    private JLabel succesfulPasswordChangeLable;

}