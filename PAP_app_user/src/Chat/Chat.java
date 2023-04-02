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

    public Chat(int user_id, int current_conv, ServerConnector server_connector) {
        current_conversation = current_conv;
        chat_accesor = new ChatAccesors(server_connector);
        JSONObject conversations = chat_accesor.get_users_conversations(user_id);
        users_conversations = new Hashtable<>();
        convert_conversations_response_to_hashtable(conversations);
        messages_in_users_conversation = new Hashtable<>();
        get_current_messages();

    }

    // private Hashtable<String, String> _convert_JSON_to_hashtable(JSONObject
    // json_object) {
    // Hashtable<String, String> to_return = new Hashtable<>();
    // Set<String> keys = json_object.keySet();
    // for (String key : keys) {
    // if (json_object.get(key) instanceof Integer) {
    // to_return.put(key, Integer.toString(json_object.getInt(key)));
    // } else {
    // to_return.put(key, json_object.getString(key));
    // }
    // }
    // return to_return;
    // }
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

    private void convert_conversations_response_to_hashtable(JSONObject response) {
        JSONArray conversations = response.getJSONArray("value");
        for (int i = 0; i < conversations.length(); i += 1) {
            users_conversations.put(conversations.getJSONObject(i).getInt("ID"), response);
        }
    }

}
