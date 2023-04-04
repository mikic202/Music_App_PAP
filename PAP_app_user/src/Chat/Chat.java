package Chat;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import ServerConnector.ServerConnector;

public class Chat {

    Hashtable<Integer, JSONObject> users_conversations;
    Hashtable<Integer, ArrayList<JSONObject>> messages_in_users_conversation;
    ChatAccesors chat_accesor;
    int current_conversation;
    int user_id;

    public Chat(int user_id, int current_conv, ServerConnector server_connector) {
        current_conversation = current_conv;
        this.user_id = user_id;
        chat_accesor = new ChatAccesors(server_connector);
        JSONObject conversations = chat_accesor.get_users_conversations(user_id);
        users_conversations = new Hashtable<>();
        convert_conversations_response_to_hashtable(conversations);
        messages_in_users_conversation = new Hashtable<>();
        get_current_messages();

    }

    public ArrayList<JSONObject> switch_conversations(int new_coveration) throws Exception {
        if (!set_current_conversation(new_coveration)) {
            throw new Exception("user can't acces given conversation");
        }

        return get_current_messages();
    }

    public boolean set_current_conversation(int new_current_conv) {
        if (!users_conversations.containsKey(new_current_conv)) {
            return false;
        }
        current_conversation = new_current_conv;
        return true;
    }

    public ArrayList<JSONObject> get_current_messages() {
        if (messages_in_users_conversation.containsKey(current_conversation)) {
            return messages_in_users_conversation.get(current_conversation);
        }
        ArrayList<JSONObject> new_messages = new ArrayList<>();
        JSONObject response = chat_accesor.get_messages_in_conversation(current_conversation);
        for (int i = 0; i < response.getJSONArray("value").length(); i += 1) {
            new_messages.add(response.getJSONArray("value").getJSONObject(i));
        }
        messages_in_users_conversation.put(current_conversation, new_messages);
        return new_messages;
    }

    public JSONObject send_message(String text) {
        return chat_accesor.send_message(current_conversation, user_id, text).getJSONObject("value");
    }

    private void convert_conversations_response_to_hashtable(JSONObject response) {
        JSONArray conversations = response.getJSONArray("value");
        for (int i = 0; i < conversations.length(); i += 1) {
            users_conversations.put(conversations.getJSONObject(i).getInt("ID"), response);
        }
    }

    public JSONObject get_curent_conversation_info() {
        return users_conversations.get(current_conversation);
    }

    public JSONObject get_user_information(int id) {
        return chat_accesor.get_user_info(id).getJSONObject("value");
    }

    public JSONObject get_user_information(String username) {
        return chat_accesor.get_user_info(username).getJSONObject("value");
    }

    public JSONObject create_conversation(String name, ArrayList<Integer> users_id) {
        return chat_accesor.add_conversation(name, users_id).getJSONObject("value");
    }

    public JSONObject add_users_to_current_conversation(ArrayList<Integer> users_id) {
        return chat_accesor.add_users_to_conversation(current_conversation, users_id).getJSONObject("value");
    }

}
