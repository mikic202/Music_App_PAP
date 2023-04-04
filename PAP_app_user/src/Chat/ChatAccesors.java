package Chat;

import java.util.ArrayList;
import org.json.JSONObject;

import ServerConnector.ServerConnector;

public class ChatAccesors {
    public ChatAccesors(ServerConnector server_connector) {
        this.server_connector = server_connector;
    }

    public JSONObject send_message(int conversation_id, int sender_id, String text) {
        JSONObject procesed_request = RequestCreator.create_send_msg_request(conversation_id, sender_id, text);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject add_conversation(String name, ArrayList<Integer> users_ids) {
        JSONObject procesed_request = RequestCreator.create_add_conversation_request(name, users_ids);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject get_messages_in_conversation(int conversation_id) {
        JSONObject procesed_request = RequestCreator.create_get_messages_request(conversation_id);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject get_users_conversations(int user_id) {
        JSONObject procesed_request = RequestCreator.create_get_conversations_request(user_id);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject add_users_to_conversation(int conversation_id, ArrayList<Integer> users_ids) {
        JSONObject procesed_request = RequestCreator.create_add_user_to_conversation_request(conversation_id,
                users_ids);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject get_user_info(int user_id) {
        JSONObject request = RequestCreator.create_get_user_information_request(user_id);
        JSONObject response = server_connector.send_request(request);
        return response;
    }

    public JSONObject get_user_info(String username) {
        JSONObject request = RequestCreator.create_get_user_information_request(username);
        JSONObject response = server_connector.send_request(request);
        return response;
    }

    private ServerConnector server_connector;

}