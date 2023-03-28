package Chat;

public class RequestCreator {
    public static String create_send_msg_request(int conversation_id, int sender_id, String text) {
        return String.format("%s;%d;%d;%s", RequestTypes.SEND_MESSAGE.value(), sender_id, conversation_id,
                text);
    }
}
