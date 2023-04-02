package Chat;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import org.json.JSONObject;

import ServerConnector.ServerConnector;

public class ChatSender {
    public ChatSender() {
        server_connector = new ServerConnector();
    }

    public JSONObject send_message(int conversation_id, int sender_id, String text) {
        JSONObject procesed_request = RequestCreator.create_send_msg_request(conversation_id, sender_id, text);
        JSONObject response = server_connector.send_request(procesed_request);
        return response.getJSONObject("value");
    }

    public JSONObject add_conversation(String name, ArrayList<Integer> users_ids) {
        JSONObject procesed_request = RequestCreator.create_add_conversation_request(name, users_ids);
        JSONObject response = server_connector.send_request(procesed_request);
        return response.getJSONObject("value");
    }

    public JSONObject get_messages_in_conversation(int conversation_id) {
        JSONObject procesed_request = RequestCreator.create_get_messages_request(conversation_id);
        JSONObject response = server_connector.send_request(procesed_request);
        return response.getJSONObject("value");
    }

    public JSONObject get_users_conversations(int user_id) {
        JSONObject procesed_request = RequestCreator.create_get_conversations_request(user_id);
        JSONObject response = server_connector.send_request(procesed_request);
        return response.getJSONObject("value");
    }

    public JSONObject add_users_to_conversation(int conversation_id, ArrayList<Integer> users_ids) {
        JSONObject procesed_request = RequestCreator.create_add_user_to_conversation_request(conversation_id,
                users_ids);
        JSONObject response = server_connector.send_request(procesed_request);
        return response.getJSONObject("value");
    }

    private ServerConnector server_connector;

}