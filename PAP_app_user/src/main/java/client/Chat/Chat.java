package client.Chat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import client.ServerConnector.ServerConnector;

public class Chat {

    private Hashtable<Integer, JSONObject> users_conversations;
    private Hashtable<Integer, ArrayList<JSONObject>> messages_in_users_conversation;
    private Hashtable<Integer, Hashtable<Integer, JSONObject>> users_in_conversarion;
    private ChatAccesors chat_accesor;
    private int current_conversation;
    private int user_id;

    public Chat(int user_id, int current_conv, ServerConnector server_connector) {
        current_conversation = current_conv;
        this.user_id = user_id;
        chat_accesor = new ChatAccesors(server_connector);
        JSONObject conversations = chat_accesor.get_users_conversations(user_id);
        users_conversations = new Hashtable<>();
        convert_conversations_response_to_hashtable(conversations);
        if (current_conversation == -1 && users_conversations.size() != 0) {
            current_conversation = Collections.min(users_conversations.keySet());
        }
        messages_in_users_conversation = new Hashtable<>();
        get_current_messages();
        users_in_conversarion = new Hashtable<>();
        get_users_in_conversation(current_conv);

    }

    public ArrayList<JSONObject> switch_conversations(int new_coveration) throws Exception {
        if (!set_current_conversation(new_coveration)) {
            throw new Exception("user can't acces given conversation");
        }
        // update_status();

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
        users_conversations.clear();
        JSONArray conversations = response.getJSONArray("value");
        for (int i = 0; i < conversations.length(); i += 1) {
            users_conversations.put(conversations.getJSONObject(i).getInt("ID"), conversations.getJSONObject(i));
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

    public JSONObject create_conversation(String name, ArrayList<String> usernames) {
        JSONObject conversation_info = chat_accesor.add_conversation(name, usernames).getJSONObject("value");
        users_conversations.put(conversation_info.getInt("ID"), conversation_info);
        return conversation_info;
    }

    public JSONObject add_users_to_conversation(String conversation_name, ArrayList<String> usernames) {
        // TODO adding users to users in conversation hashtable
        for (Integer key : users_conversations.keySet()) {
            if (users_conversations.get(key).getString("name").equals(conversation_name)) {
                if (users_in_conversarion.get(key) == null) {
                    get_users_in_conversation(key);
                }
                for (String username : usernames) {
                    System.out.println("\n");
                    System.out.println(users_in_conversarion.get(key));
                    JSONObject user_info = chat_accesor.get_user_info(username).getJSONObject("value");
                    users_in_conversarion.get(key).put(user_info.getInt("ID"), user_info);
                    System.out.println(users_in_conversarion.get(key));
                }
                return chat_accesor.add_users_to_conversation(key, usernames).getJSONObject("value");
            }
        }
        return new JSONObject("{\"outcome\":false}");
    }

    public Hashtable<String, Integer> get_conversations_names_to_ids() {
        Hashtable<String, Integer> conv = new Hashtable<>();
        for (int id_key : users_conversations.keySet()) {
            conv.put(users_conversations.get(id_key).getString("name"), id_key);
        }
        return conv;
    }

    public Hashtable<Integer, JSONObject> get_users_in_current_conversation() {
        return get_users_in_conversation(current_conversation);
    }

    private Hashtable<Integer, JSONObject> get_users_in_conversation(int conversation) {
        if (users_in_conversarion.containsKey(conversation)) {
            return users_in_conversarion.get(conversation);
        }
        Hashtable<Integer, JSONObject> users_in_conv = new Hashtable<Integer, JSONObject>();
        JSONArray users = chat_accesor.get_users_in_conversation(conversation).getJSONArray("value");
        for (int i = 0; i < users.length(); i += 1) {
            users_in_conv.put(users.getInt(i), get_user_information(users.getInt(i)));
        }
        users_in_conversarion.put(conversation, users_in_conv);
        return users_in_conv;
    }

    public int user_id() {
        return user_id;
    }

    public void update_status() {
        if (users_conversations.size() == 0) {
            return;
        }
        JSONObject conversations = chat_accesor.get_users_conversations(user_id);
        convert_conversations_response_to_hashtable(conversations);

        int latest_msg = 1;
        if (messages_in_users_conversation.get(current_conversation) != null
                && messages_in_users_conversation.get(current_conversation).size() != 0) {
            latest_msg = messages_in_users_conversation.get(current_conversation)
                    .get(messages_in_users_conversation.get(current_conversation).size() - 1).getInt("ID");
        }
        JSONObject new_messages_response = chat_accesor.get_new_messages_in_converastion(current_conversation,
                latest_msg);
        for (int i = 0; i < new_messages_response.getJSONArray("value").length(); i += 1) {
            ArrayList<JSONObject> messages = messages_in_users_conversation.get(current_conversation);
            messages.add(new_messages_response.getJSONArray("value").getJSONObject(i));
            messages_in_users_conversation.replace(current_conversation, messages);
        }
    }

}
