package client.login_and_account_accessors;

import org.json.JSONObject;
import java.awt.image.BufferedImage;

import client.ServerConnector.ServerConnector;

public class AccountChangeRequestAccessors {
    public AccountChangeRequestAccessors(ServerConnector server_connector) {
        this.server_connector = server_connector;
    }

    public JSONObject sendAccountData(String email, String nickname, char[] old_password, char[] new_password,
            char[] confirm_new_password) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_account_request(email, nickname,
                old_password, new_password, confirm_new_password);
        JSONObject response = server_connector.sendRequest(procesed_request);
        return response;
    }

    public JSONObject sendUserRegisterData(BufferedImage avatar) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_avatar_request(avatar);
        JSONObject response = server_connector.sendRequest(procesed_request);
        return response;
    }

    public JSONObject sendUserAccountEmailData(String email, int userId) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_email_request(email, userId);
        JSONObject response = server_connector.sendRequest(procesed_request);
        return response;
    }

    public JSONObject sendUserAccountNickanameData(String nickname, int userId) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_nickname_request(nickname, userId);
        JSONObject response = server_connector.sendRequest(procesed_request);
        return response;
    }

    public JSONObject sendUserAccountNewPasswordData(char[] new_password, char[] old_password, int userId) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_new_password_request(new_password,
                old_password, userId);
        JSONObject response = server_connector.sendRequest(procesed_request);
        return response;
    }

    public JSONObject sendUserAccountPasswordData(char[] password) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_password_request(password);
        JSONObject response = server_connector.sendRequest(procesed_request);
        return response;
    }

    private ServerConnector server_connector;
}
