package client.login_and_account_accessors;

import org.json.JSONObject;
import java.awt.image.BufferedImage;

import client.ServerConnector.ServerConnector;

public class AccountChangeRequestAccessors {
    public AccountChangeRequestAccessors(ServerConnector server_connector) {
        this.server_connector = server_connector;
    }

    public JSONObject send_account_data(String email, String nickname, char[] old_password, char[] new_password,
            char[] confirm_new_password) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_account_request(email, nickname,
                old_password, new_password, confirm_new_password);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject send_user_register_data(BufferedImage avatar) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_avatar_request(avatar);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject send_user_account_email_data(String email, int user_id) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_email_request(email, user_id);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject send_user_account_nickaname_data(String nickname, int user_id) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_nickname_request(nickname, user_id);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject send_user_account_new_password_data(char[] new_password, char[] old_password, int user_id) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_new_password_request(new_password,
                old_password, user_id);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject send_user_account_password_data(char[] password) {
        JSONObject procesed_request = AccountChangeRequestCreator.create_send_password_request(password);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject send_user_account_confirm_new_password_data(char[] confirm_new_password) {
        // JSONObject procesed_request = AccountChangeRequestCreator
        // .create_send_confirm_new_password_request(confirm_new_password);
        // JSONObject response = server_connector.send_request(procesed_request);
        // return response;
        return new JSONObject();
    }

    private ServerConnector server_connector;
}
