package client.GUI;
import org.json.JSONObject;
import java.awt.image.BufferedImage;

public class AccountChangeRequestCreator {
    public static JSONObject create_send_account_request(String email, String nickname, char[] old_password, char[] new_password, char[] confirm_new_password) {
        return new JSONObject(
                        String.format("{\"type\":\"%s\", \"value\":{\"email\":%s, \"nickname\":%s, \"old_password\":%s, \"new_password\":%s, \"confirm_new_password\":%s}}",
                                        AccountChangeRequestTypes.SEND_DATA.value(), email, nickname, new String(old_password), new String(new_password), new String(confirm_new_password)));
    }

    public static JSONObject create_send_avatar_request(BufferedImage avatar) {
        return new JSONObject(
                        String.format("{\"type\":\"%s\", \"value\":{\"avatar\":%BufferedImage}}",
                                        AccountChangeRequestTypes.SEND_AVATAR.value(), avatar));
    }
}
