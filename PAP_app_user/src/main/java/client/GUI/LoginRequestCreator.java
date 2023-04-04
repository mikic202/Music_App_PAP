package client.GUI;

import org.json.JSONObject;

public class LoginRequestCreator {
        public static JSONObject create_send_login_request(String email, char[] password) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"email\":%s, \"password\":%s}}",
                                                LoginRequestTypes.SEND_LOGIN.value(), email, password.toString()));
        }

        public static JSONObject create_send_registration_request(String email, String nickname, char[]password, char[]confirm_password) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"email\":%s, \"nickname\":%s, \"password\":%s, \"confirm_password\":%s}}",
                                                LoginRequestTypes.SEND_REGISTER.value(), email, nickname, password.toString(), confirm_password.toString()));
        }
}
