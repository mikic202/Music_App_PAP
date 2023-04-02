package Chat;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import org.json.JSONObject;

public class ChatSender {
    public ChatSender(Socket msg_socket) {
        message_socket = msg_socket;
    }

    public ChatSender() {

    }

    public Hashtable<String, String> send_message(int conversation_id, int sender_id, String text) {
        Hashtable<String, String> added_msg_data = new Hashtable<>();
        JSONObject procesed_request = RequestCreator.create_send_msg_request(conversation_id, sender_id, text);
        return added_msg_data;
    }

    public Hashtable<String, String> add_conversation(String name, ArrayList<Integer> users_ids) {
        Hashtable<String, String> added_conversation = new Hashtable<>();
        JSONObject procesed_request = RequestCreator.create_add_conversation_request(name, users_ids);
        return added_conversation;
    }

    public Hashtable<String, String> get_messages_in_conversation(int conversation_id) {
        Hashtable<String, String> msg_data = new Hashtable<>();
        JSONObject procesed_request = RequestCreator.create_get_messages_request(conversation_id);
        return msg_data;
    }

    public Hashtable<String, String> get_users_conversations(int user_id) {
        Hashtable<String, String> conversation_data = new Hashtable<>();
        JSONObject procesed_request = RequestCreator.create_get_conversations_request(user_id);
        return conversation_data;
    }

    public Hashtable<String, String> add_users_to_conversation(int conversation_id, ArrayList<Integer> users_ids) {
        Hashtable<String, String> outcome = new Hashtable<>();
        JSONObject procesed_request = RequestCreator.create_add_user_to_conversation_request(conversation_id,
                users_ids);
        return outcome;
    }

    private Socket message_socket;
}