package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.json.JSONObject;
import client.Chat.Chat;
import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.ServerConnector.ServerConnector;
import client.login_and_account_accessors.AccountChangeRequestAccessors;

public class ChangeUsernameListener implements ActionListener {

    public ChangeUsernameListener(JTextField newUsernameFIeld,
            ServerConnector serverConnector, Chat chat, JLabel usernameLable) {
        this.newUsernameFIeld = newUsernameFIeld;
        this.serverConnector = serverConnector;
        this.chat = chat;
        accountAccesor = new AccountChangeRequestAccessors(serverConnector);
        this.usernameLable = usernameLable;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (!newUsernameFIeld.getText().equals("")) {
            new Thread(new ChangeUsernameGuiUpdater()).start();
        }
    }

    class ChangeUsernameGuiUpdater implements Runnable {

        @Override
        public void run() {
            String newEmail = newUsernameFIeld.getText();
            JSONObject response = accountAccesor.sendUserAccountNickanameData(newEmail,
                    chat.getCurrentUserInfo().getInt(ChatMessagesConstants.USER_ID.value()));
            if (response.getJSONObject(MessagesTopLevelConstants.VALUE.value())
                    .getBoolean(MessagesTopLevelConstants.OUTCOME.value())) {
                usernameLable.setText(newEmail);
            }
        }

    }

    private JTextField newUsernameFIeld;
    private ServerConnector serverConnector;
    private AccountChangeRequestAccessors accountAccesor;
    private Chat chat;
    private JLabel usernameLable;

}
