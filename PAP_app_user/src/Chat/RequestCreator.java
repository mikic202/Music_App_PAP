package Chat;

import java.util.ArrayList;

public class RequestCreator {
    public static String create_send_msg_request(int conversation_id, int sender_id, String text) {
        return String.format("%s;%d;%d;%s", RequestTypes.SEND_MESSAGE.value(), sender_id, conversation_id,
                text);
    }

    public static String create_add_conversation_request(String name, ArrayList<Integer> users) {
        String request = RequestTypes.CREATE_CONVERSATION.value();
        request += ";" + name;
        for (int user_id : users) {
            request += ";" + Integer.toString(user_id);
        }
        return request;
    }

    public static String create_get_messages_request(int conversation_id) {
        return String.format("%s;%d", RequestTypes.GET_MESSAGES.value(), conversation_id);

    }

    public static String create_get_conversations_request(int user_id) {
        return String.format("%s;%d", RequestTypes.GET_USERS_CONVERSATIONS.value(), user_id);
    }
}
