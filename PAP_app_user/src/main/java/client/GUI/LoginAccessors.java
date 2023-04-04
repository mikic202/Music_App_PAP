package client.GUI;
import org.json.JSONObject;

import client.ServerConnector.ServerConnector;

public class LoginAccessors {
    public LoginAccessors(ServerConnector server_connector) {
        this.server_connector = server_connector;
    }

    public JSONObject send_user_login_data(String email, char[] password) {
        JSONObject procesed_request = LoginRequestCreator.create_send_login_request(email, password);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject add_conversation(String email, String nickname, char[] password, char[] confirm_password) {
        JSONObject procesed_request = LoginRequestCreator.create_send_registration_request(email, nickname, password, confirm_password);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    private ServerConnector server_connector;

}