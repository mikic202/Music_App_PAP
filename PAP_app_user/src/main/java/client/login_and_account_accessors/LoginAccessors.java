package client.login_and_account_accessors;

import org.json.JSONObject;

import client.ServerConnector.ServerConnector;

public class LoginAccessors {
    public LoginAccessors(ServerConnector server_connector) {
        this.server_connector = server_connector;
    }

    public JSONObject sendUserLoginData(String email, char[] password) {
        JSONObject procesed_request = LoginRequestCreator.createSendLoginRequest(email, password);
        JSONObject response = server_connector.sendRequest(procesed_request);
        return response;
    }

    public JSONObject sendUserRegisterData(String email, String nickname, char[] password, char[] confirm_password) {
        JSONObject procesed_request = LoginRequestCreator.createSendRegistrationRequest(email, nickname, password,
                confirm_password);
        JSONObject response = server_connector.sendRequest(procesed_request);
        return response;
    }

    public JSONObject sendUserChangePasswordData(String nickname, char[] old_password, char[] confirm_old_password,
            char[] new_password, char[] confirm_new_password) {
        JSONObject procesed_request = LoginRequestCreator.createChangePasswordRequest(nickname, old_password,
                confirm_old_password, new_password, confirm_new_password);
        JSONObject response = server_connector.sendRequest(procesed_request);
        return response;
    }

    public JSONObject sendRetrievePassword(String email) {
        JSONObject procesed_request = LoginRequestCreator.createRetrievePassword(email);
        JSONObject response = server_connector.sendRequest(procesed_request);
        return response;
    }

    private ServerConnector server_connector;

}