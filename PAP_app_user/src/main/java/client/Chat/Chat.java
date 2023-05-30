package client.Chat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import client.ServerConnector.ServerConnector;
import java.awt.image.BufferedImage;

public class Chat {

    private Hashtable<Integer, JSONObject> usersConversations;
    private Hashtable<Integer, ArrayList<JSONObject>> messagesInUsersConversation;
    private Hashtable<Integer, Hashtable<Integer, JSONObject>> usersInConversarion;
    private Hashtable<Integer, JSONObject> usersEncountered;
    private ChatAccesors chatAccesor;
    private int currentConversation;
    private int userId;
    private JSONObject userInfo;

    public Chat(JSONObject userInfo, int currentConv, ServerConnector serverConnector) {
        currentConversation = currentConv;
        this.userInfo = userInfo;
        this.userId = userInfo.getInt("user_id");
        usersEncountered = new Hashtable<>();
        usersEncountered.put(this.userId, this.userInfo);
        chatAccesor = new ChatAccesors(serverConnector);
        JSONObject conversations = chatAccesor.getUsersConversations(userId);
        usersConversations = new Hashtable<>();
        convertConversationsResponseToHashtable(conversations);
        if (currentConversation == -1 && usersConversations.size() != 0) {
            currentConversation = Collections.min(usersConversations.keySet());
        }
        messagesInUsersConversation = new Hashtable<>();
        getCurrentMessages();
        usersInConversarion = new Hashtable<>();
        getUsersInConversation(currentConversation);

    }

    public JSONObject getCurrentUserInfo() {
        return userInfo;
    }

    public JSONObject getCurrerntConversationInfo() {
        return usersConversations.get(currentConversation);
    }

    public ArrayList<JSONObject> switchConversations(int new_coveration) throws Exception {
        if (!setCurrentConversation(new_coveration)) {
            throw new Exception("user can't acces given conversation");
        }
        // updateStatus();

        return getCurrentMessages();
    }

    public ArrayList<JSONObject> switchConversations(String conversationName) throws Exception {
        if (!setCurrentConversation(conversationName)) {
            throw new Exception("user can't acces given conversation");
        }
        // updateStatus();

        return getCurrentMessages();
    }

    public boolean setCurrentConversation(int new_currentConv) {
        if (!usersConversations.containsKey(new_currentConv)) {
            return false;
        }
        currentConversation = new_currentConv;
        return true;
    }

    public boolean setCurrentConversation(String conversationName) {
        int conversationId = getConversationsNamesToIds().get(conversationName);
        return setCurrentConversation(conversationId);
    }

    public ArrayList<JSONObject> getCurrentMessages() {
        if (messagesInUsersConversation.containsKey(currentConversation)) {
            return messagesInUsersConversation.get(currentConversation);
        }
        ArrayList<JSONObject> new_messages = new ArrayList<>();
        JSONObject response = chatAccesor.getMessagesInConversation(currentConversation);
        for (int i = 0; i < response.getJSONArray("value").length(); i += 1) {
            new_messages.add(response.getJSONArray("value").getJSONObject(i));
        }
        messagesInUsersConversation.put(currentConversation, new_messages);
        return new_messages;
    }

    public JSONObject sendMessage(String text) {
        JSONObject newMessage = chatAccesor.sendMessage(currentConversation, userId, text).getJSONObject("value");
        messagesInUsersConversation.get(currentConversation).add(newMessage);
        return newMessage;
    }

    private void convertConversationsResponseToHashtable(JSONObject response) {
        usersConversations.clear();
        JSONArray conversations = response.getJSONArray("value");
        for (int i = 0; i < conversations.length(); i += 1) {
            usersConversations.put(conversations.getJSONObject(i).getInt("conversation_id"),
                    conversations.getJSONObject(i));
        }
    }

    public JSONObject getCurentConversationInfo() {
        return usersConversations.get(currentConversation);
    }

    public JSONObject getUserInformation(int id) {
        if (usersEncountered.keySet().contains(id)) {
            return usersEncountered.get(id);
        }
        var newUser = chatAccesor.getUserInfo(id).getJSONObject("value");
        usersEncountered.put(newUser.getInt("ID"), newUser);
        return newUser;
    }

    public JSONObject getUserInformation(String username) {
        return chatAccesor.getUserInfo(username).getJSONObject("value");
    }

    public JSONObject createConversation(String name, ArrayList<String> usernames) {
        JSONObject conversation_info = chatAccesor.addConversation(name, usernames).getJSONObject("value");
        usersConversations.put(conversation_info.getInt("conversation_id"), conversation_info);
        return conversation_info;
    }

    public JSONObject addUsersToConversation(String conversation_name, ArrayList<String> usernames) {
        for (Integer key : usersConversations.keySet()) {
            if (usersConversations.get(key).getString("name").equals(conversation_name)) {
                if (usersInConversarion.get(key) == null) {
                    getUsersInConversation(key);
                }
                for (String username : usernames) {
                    JSONObject user_info = chatAccesor.getUserInfo(username).getJSONObject("value");
                    usersInConversarion.get(key).put(user_info.getInt("ID"), user_info);
                }
                return chatAccesor.addUsersToConversation(key, usernames).getJSONObject("value");
            }
        }
        return new JSONObject("{\"outcome\":false}");
    }

    public JSONObject addUsersToCurrentConversation(ArrayList<String> usernames) {
        return addUsersToConversation(usersConversations.get(currentConversation).getString("name"), usernames);
    }

    public Hashtable<String, Integer> getConversationsNamesToIds() {
        Hashtable<String, Integer> conv = new Hashtable<>();
        for (int id_key : usersConversations.keySet()) {
            conv.put(usersConversations.get(id_key).getString("name"), id_key);
        }
        return conv;
    }

    public Hashtable<Integer, JSONObject> getUsersInCurrentConversation() {
        return getUsersInConversation(currentConversation);
    }

    private Hashtable<Integer, JSONObject> getUsersInConversation(int conversation) {
        if (usersInConversarion.containsKey(conversation)) {
            return usersInConversarion.get(conversation);
        }
        Hashtable<Integer, JSONObject> users_in_conv = new Hashtable<Integer, JSONObject>();
        JSONArray users = chatAccesor.getUsersInConversation(conversation).getJSONArray("value");
        for (int i = 0; i < users.length(); i += 1) {
            users_in_conv.put(users.getInt(i), getUserInformation(users.getInt(i)));
        }
        usersInConversarion.put(conversation, users_in_conv);
        return users_in_conv;
    }

    public int userId() {
        return userId;
    }

    public void updateStatus() {
        if (usersConversations.size() == 0) {
            return;
        }
        JSONObject conversations = chatAccesor.getUsersConversations(userId);
        convertConversationsResponseToHashtable(conversations);

        int latest_msg = 1;
        if (messagesInUsersConversation.get(currentConversation) != null
                && messagesInUsersConversation.get(currentConversation).size() != 0) {
            latest_msg = messagesInUsersConversation.get(currentConversation)
                    .get(messagesInUsersConversation.get(currentConversation).size() - 1).getInt("message_id");
        }
        JSONObject new_messages_response = chatAccesor.getNewMessagesInConverastion(currentConversation,
                latest_msg);
        for (int i = 0; i < new_messages_response.getJSONArray("value").length(); i += 1) {
            ArrayList<JSONObject> messages = messagesInUsersConversation.get(currentConversation);
            messages.add(new_messages_response.getJSONArray("value").getJSONObject(i));
            messagesInUsersConversation.replace(currentConversation, messages);
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
        return chatAccesor.sendImage(currentConversation, userId, data, format);
    }

    public String getConversationCode() {
        JSONObject response = chatAccesor.getConversationCode(currentConversation);
        return response.getJSONObject("value").getString("conversation code");
    }

    public boolean joinConversationUsingCode(String code) {
        JSONObject response = chatAccesor.joinConversationUsingCode(code, userId);
        boolean outcome = response.getJSONObject("value").getBoolean("outcome");
        if (outcome) {
            JSONObject conversations = chatAccesor.getUsersConversations(userId);
            convertConversationsResponseToHashtable(conversations);
        }
        return outcome;
    }

    public void addExternalMessage(JSONObject message) {
        if (messagesInUsersConversation.keySet().contains(message.getInt("conversation_id"))
                && message.getInt("sender_id") != userId) {
            messagesInUsersConversation.get(message.getInt("conversation_id")).add(message);
        }
    }

}
