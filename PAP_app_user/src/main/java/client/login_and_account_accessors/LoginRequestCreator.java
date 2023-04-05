package client.login_and_account_accessors;

import org.json.JSONObject;

public class LoginRequestCreator {
        public static JSONObject create_send_login_request(String email, char[] password) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"email\":%s, \"password\":%s}}",
                                                LoginRequestTypes.SEND_LOGIN.value(), email, String.valueOf(password)));
        }

        public static JSONObject create_send_registration_request(String email, String nickname, char[] password,
                        char[] confirm_password) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"email\":%s, \"nickname\":%s, \"password\":%s, \"confirm_password\":%s}}",
                                                LoginRequestTypes.SEND_REGISTER.value(), email, nickname,
                                                new String(password), new String(confirm_password)));
        }

        public static JSONObject create_change_password_request(String nickname, char[] old_password,
                        char[] confirm_old_password, char[] new_password, char[] confirm_new_password) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"nickname\":%s, \"old_password\":%s, \"confirm_old_password\":%s, \"new_password\":%s, \"confirm_new_password\":%s}}",
                                                LoginRequestTypes.SEND_CHANGE_PASSWORD.value(), nickname,
                                                new String(old_password), new String(confirm_old_password),
                                                new String(new_password), new String(confirm_new_password)));
        }
}
