package Chat;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import org.json.JSONObject;

import ServerConnector.ServerConnector;

public class ChatSender {
    public ChatSender(Socket msg_socket) {
        message_socket = msg_socket;
    }

    public ChatSender() {

    }

    public Hashtable<String, String> send_message(int conversation_id, int sender_id, String text) {
        Hashtable<String, String> added_msg_data = new Hashtable<>();
        JSONObject procesed_request = RequestCreator.create_send_msg_request(conversation_id, sender_id, text);
        ServerConnector server_connector = new ServerConnector();
        JSONObject response = server_connector.send_request(procesed_request);
        return added_msg_data;
    }

    public Hashtable<String, String> add_conversation(String name, ArrayList<Integer> users_ids) {
        Hashtable<String, String> added_msg_data = new Hashtable<>();
        JSONObject procesed_request = RequestCreator.create_add_conversation_request(name, users_ids);
        ServerConnector server_connector = new ServerConnector();
        JSONObject response = server_connector.send_request(procesed_request);
        return added_msg_data;
    }

    public Hashtable<String, String> get_messages_in_conversation(int conversation_id) {
        Hashtable<String, String> added_msg_data = new Hashtable<>();
        JSONObject procesed_request = RequestCreator.create_get_messages_request(conversation_id);
        ServerConnector server_connector = new ServerConnector();
        JSONObject response = server_connector.send_request(procesed_request);
        return added_msg_data;
    }

    public Hashtable<String, String> get_users_conversations(int user_id) {
        Hashtable<String, String> added_msg_data = new Hashtable<>();
        JSONObject procesed_request = RequestCreator.create_get_conversations_request(user_id);
        ServerConnector server_connector = new ServerConnector();
        JSONObject response = server_connector.send_request(procesed_request);
        return added_msg_data;
    }

    public Hashtable<String, String> add_users_to_conversation(int conversation_id, ArrayList<Integer> users_ids) {
        Hashtable<String, String> added_msg_data = new Hashtable<>();
        JSONObject procesed_request = RequestCreator.create_add_user_to_conversation_request(conversation_id,
                users_ids);
        ServerConnector server_connector = new ServerConnector();
        JSONObject response = server_connector.send_request(procesed_request);
        return added_msg_data;
    }

}