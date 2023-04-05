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

    private ServerConnector server_connector;
}
