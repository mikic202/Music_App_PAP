package client.login_and_account_accessors;

import org.json.JSONObject;
import java.awt.image.BufferedImage;

public class AccountChangeRequestCreator {
        public static JSONObject create_send_account_request(String email, String nickname, char[] old_password,
                        char[] new_password, char[] confirm_new_password) {
                return new JSONObject(
                                String.format(
                                                "{\"type\":\"%s\", \"value\":{\"email\":%s, \"nickname\":%s, \"old_password\":%s, \"new_password\":%s, \"confirm_new_password\":%s}}",
                                                AccountChangeRequestTypes.SEND_DATA.value(), email, nickname,
                                                new String(old_password),
                                                new String(new_password), new String(confirm_new_password)));
        }

        public static JSONObject create_send_avatar_request(BufferedImage avatar) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"avatar\":%BufferedImage}}",
                                                AccountChangeRequestTypes.SEND_AVATAR.value(), avatar));
        }

        public static JSONObject create_send_email_request(String email, int userId) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"email\":%s, \"user_id\":%d}}",
                                                LoginRequestTypes.SEND_CHANGE_EMAIL.value(), email, userId));
        }

        public static JSONObject create_send_nickname_request(String nickname, int userId) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"nickname\":%s, \"user_id\":%d}}",
                                                LoginRequestTypes.SEND_CHANGE_NICKNAME.value(), nickname, userId));
        }

        public static JSONObject create_send_new_password_request(char[] new_password, char[] old_password,
                        int userId) {
                return new JSONObject(
                                String.format(
                                                "{\"type\":\"%s\", \"value\":{\"new_password\":\"%s\", \"old_password\":\"%s\", \"user_id\":%d}}",
                                                LoginRequestTypes.SEND_CHANGE_PASSWORD.value(),
                                                new String(new_password),
                                                new String(old_password), userId));
        }

        public static JSONObject create_send_password_request(char[] password) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"password\":%s}}",
                                                AccountChangeRequestTypes.SEND_PASSWORD.value(), new String(password)));
        }

        public static JSONObject create_send_confirm_new_password_request(String confirm_new_password) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"confirm_new_password\":%s}}",
                                                AccountChangeRequestTypes.SEND_CONFIRM_NEW_PASSWORD.value(),
                                                confirm_new_password));
        }
}
