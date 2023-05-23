package client.Chat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import client.ServerConnector.ServerConnector;
import java.awt.image.BufferedImage;

public class Chat {

    private Hashtable<Integer, JSONObject> users_conversations;
    private Hashtable<Integer, ArrayList<JSONObject>> messagesInUsersConversation;
    private Hashtable<Integer, Hashtable<Integer, JSONObject>> users_in_conversarion;
    private ChatAccesors chatAccesor;
    private int current_conversation;
    private int userId;
    private JSONObject userInfo;

    public Chat(JSONObject userInfo, int current_conv, ServerConnector server_connector) {
        current_conversation = current_conv;
        this.userInfo = userInfo;
        this.userId = userInfo.getInt("user_id");
        chatAccesor = new ChatAccesors(server_connector);
        JSONObject conversations = chatAccesor.getUsersConversations(userId);
        users_conversations = new Hashtable<>();
        convert_conversationsResponseToHashtable(conversations);
        if (current_conversation == -1 && users_conversations.size() != 0) {
            current_conversation = Collections.min(users_conversations.keySet());
        }
        messagesInUsersConversation = new Hashtable<>();
        getCurrentMessages();
        users_in_conversarion = new Hashtable<>();
        getUsersInConversation(current_conv);

    }

    public JSONObject getCurrentUserInfo() {
        return userInfo;
    }

    public ArrayList<JSONObject> switchConversations(int new_coveration) throws Exception {
        if (!setCurrentConversation(new_coveration)) {
            throw new Exception("user can't acces given conversation");
        }
        // updateStatus();

        return getCurrentMessages();
    }

    public boolean setCurrentConversation(int new_current_conv) {
        if (!users_conversations.containsKey(new_current_conv)) {
            return false;
        }
        current_conversation = new_current_conv;
        return true;
    }

    public ArrayList<JSONObject> getCurrentMessages() {
        if (messagesInUsersConversation.containsKey(current_conversation)) {
            return messagesInUsersConversation.get(current_conversation);
        }
        ArrayList<JSONObject> new_messages = new ArrayList<>();
        JSONObject response = chatAccesor.getMessagesInConversation(current_conversation);
        for (int i = 0; i < response.getJSONArray("value").length(); i += 1) {
            new_messages.add(response.getJSONArray("value").getJSONObject(i));
        }
        messagesInUsersConversation.put(current_conversation, new_messages);
        return new_messages;
    }

    public JSONObject sendMessage(String text) {
        JSONObject newMessage = chatAccesor.sendMessage(current_conversation, userId, text).getJSONObject("value");
        messagesInUsersConversation.get(current_conversation).add(newMessage);
        return newMessage;
    }

    private void convert_conversationsResponseToHashtable(JSONObject response) {
        users_conversations.clear();
        JSONArray conversations = response.getJSONArray("value");
        for (int i = 0; i < conversations.length(); i += 1) {
            users_conversations.put(conversations.getJSONObject(i).getInt("conversation_id"),
                    conversations.getJSONObject(i));
        }
    }

    public JSONObject getCurentConversationInfo() {
        return users_conversations.get(current_conversation);
    }

    public JSONObject getUserInformation(int id) {
        return chatAccesor.getUserInfo(id).getJSONObject("value");
    }

    public JSONObject getUserInformation(String username) {
        return chatAccesor.getUserInfo(username).getJSONObject("value");
    }

    public JSONObject createConversation(String name, ArrayList<String> usernames) {
        JSONObject conversation_info = chatAccesor.addConversation(name, usernames).getJSONObject("value");
        users_conversations.put(conversation_info.getInt("conversation_id"), conversation_info);
        return conversation_info;
    }

    public JSONObject addUsersToConversation(String conversation_name, ArrayList<String> usernames) {
        for (Integer key : users_conversations.keySet()) {
            if (users_conversations.get(key).getString("name").equals(conversation_name)) {
                if (users_in_conversarion.get(key) == null) {
                    getUsersInConversation(key);
                }
                for (String username : usernames) {
                    System.out.println("\n");
                    System.out.println(users_in_conversarion.get(key));
                    JSONObject user_info = chatAccesor.getUserInfo(username).getJSONObject("value");
                    users_in_conversarion.get(key).put(user_info.getInt("ID"), user_info);
                    System.out.println(users_in_conversarion.get(key));
                }
                return chatAccesor.addUsersToConversation(key, usernames).getJSONObject("value");
            }
        }
        return new JSONObject("{\"outcome\":false}");
    }

    public Hashtable<String, Integer> getConversationsNamesToIds() {
        Hashtable<String, Integer> conv = new Hashtable<>();
        for (int id_key : users_conversations.keySet()) {
            conv.put(users_conversations.get(id_key).getString("name"), id_key);
        }
        return conv;
    }

    public Hashtable<Integer, JSONObject> getUsersInCurrentConversation() {
        return getUsersInConversation(current_conversation);
    }

    private Hashtable<Integer, JSONObject> getUsersInConversation(int conversation) {
        if (users_in_conversarion.containsKey(conversation)) {
            return users_in_conversarion.get(conversation);
        }
        Hashtable<Integer, JSONObject> users_in_conv = new Hashtable<Integer, JSONObject>();
        JSONArray users = chatAccesor.getUsersInConversation(conversation).getJSONArray("value");
        for (int i = 0; i < users.length(); i += 1) {
            users_in_conv.put(users.getInt(i), getUserInformation(users.getInt(i)));
        }
        users_in_conversarion.put(conversation, users_in_conv);
        return users_in_conv;
    }

    public int userId() {
        return userId;
    }

    public void updateStatus() {
        if (users_conversations.size() == 0) {
            return;
        }
        JSONObject conversations = chatAccesor.getUsersConversations(userId);
        convert_conversationsResponseToHashtable(conversations);

        int latest_msg = 1;
        if (messagesInUsersConversation.get(current_conversation) != null
                && messagesInUsersConversation.get(current_conversation).size() != 0) {
            latest_msg = messagesInUsersConversation.get(current_conversation)
                    .get(messagesInUsersConversation.get(current_conversation).size() - 1).getInt("message_id");
        }
        JSONObject new_messages_response = chatAccesor.getNewMessagesInConverastion(current_conversation,
                latest_msg);
        for (int i = 0; i < new_messages_response.getJSONArray("value").length(); i += 1) {
            ArrayList<JSONObject> messages = messagesInUsersConversation.get(current_conversation);
            messages.add(new_messages_response.getJSONArray("value").getJSONObject(i));
            messagesInUsersConversation.replace(current_conversation, messages);
        }
    }

    public JSONObject sendImage(String path) {
        String format = "";
        int dotIndex = path.lastIndexOf(".");
        byte data[] = {};
        if (dotIndex != 0) {
            format = path.substring(dotIndex + 1);
        }
        try {
            BufferedImage bimage = ImageIO.read(new File(path));
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ImageIO.write(bimage, format, byteStream);
            data = byteStream.toByteArray();
        } catch (Exception e) {
            System.out.println(e);
        }
        return chatAccesor.sendImage(current_conversation, userId, data, format);
    }

    public String getConversationCode() {
        JSONObject response = chatAccesor.getConversationCode(current_conversation);
        return response.getJSONObject("value").getString("conversation code");
    }

    public boolean joinConversationUsingCode(String code) {
        JSONObject response = chatAccesor.joinConversationUsingCode(code, userId);
        boolean outcome = response.getJSONObject("value").getBoolean("outcome");
        if (outcome) {
            JSONObject conversations = chatAccesor.getUsersConversations(userId);
            convert_conversationsResponseToHashtable(conversations);
        }
        return outcome;
    }

}
