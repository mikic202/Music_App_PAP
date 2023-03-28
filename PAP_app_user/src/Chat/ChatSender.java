package Chat;

import java.net.Socket;
import java.util.Hashtable;

public class ChatSender {
    public ChatSender(Socket msg_socket) {
        message_socket = msg_socket;
    }

    public ChatSender() {

    }

    public Hashtable<String, String> send_message(int conversation_id, int sender_id, String text) {
        Hashtable<String, String> added_msg_data = new Hashtable<>();
        String procesed_request = RequestCreator.create_send_msg_request(conversation_id, sender_id, text);
        return added_msg_data;
    }

    private Socket message_socket;
}