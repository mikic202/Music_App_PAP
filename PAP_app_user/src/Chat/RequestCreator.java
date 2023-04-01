package Chat;

import java.util.ArrayList;

import org.json.JSONObject;

public class RequestCreator {
    public static JSONObject create_send_msg_request(int conversation_id, int sender_id, String text) {
        return new JSONObject(
                String.format("{\"type\":\"%s\", \"value\":{\"sender_id\":%d, \"conversation_id\":%d, \"text\":%s}}",
                        RequestTypes.SEND_MESSAGE.value(), sender_id, conversation_id, text));
    }

    public static JSONObject create_add_conversation_request(String name, ArrayList<Integer> users) {
        return new JSONObject(
                String.format("{\"type\":\"%s\", \"value\":{\"name\":%s, \"users\":%s}}",
                        RequestTypes.CREATE_CONVERSATION.value(), name, users));
    }

    public static JSONObject create_get_messages_request(int conversation_id) {
        return new JSONObject(
                String.format("{\"type\":\"%s\", \"value\":{\"conversation_id\":%s}}",
                        RequestTypes.GET_MESSAGES.value(), conversation_id));

    }

    public static JSONObject create_get_conversations_request(int user_id) {
        return new JSONObject(
                String.format("{\"type\":\"%s\", \"value\":{\"user_id\":%s}}",
                        RequestTypes.GET_USERS_CONVERSATIONS.value(), user_id));
    }

    public static JSONObject create_add_user_to_conversation_request(int conversation_id,
            ArrayList<Integer> users_ids) {
        return new JSONObject(
                String.format("{\"type\":\"%s\", \"value\":{\"conversation_id\":%s, \"users\":%s}}",
                        RequestTypes.GET_USERS_CONVERSATIONS.value(), conversation_id, users_ids));
    }
}
