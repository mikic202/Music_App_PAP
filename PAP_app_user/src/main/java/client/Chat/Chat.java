package client.Chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.ServerConnector.ServerConnector;
import client.helpers.ImageProcessor;

public class Chat {

    private Hashtable<Integer, JSONObject> usersConversations;
    private Hashtable<Integer, ArrayList<JSONObject>> messagesInUsersConversation;
    private Hashtable<Integer, ArrayList<Integer>> usersInConversarion;
    private Hashtable<Integer, JSONObject> usersEncountered;
    private ChatAccesors chatAccesor;
    private int currentConversation;
    private int userId;
    private JSONObject userInfo;

    public Chat(JSONObject userInfo, int currentConv, ServerConnector serverConnector) {
        this.userInfo = userInfo;
        this.userId = userInfo.getInt(ChatMessagesConstants.USER_ID.value());

        usersEncountered = new Hashtable<>();
        usersConversations = new Hashtable<>();
        messagesInUsersConversation = new Hashtable<>();
        usersInConversarion = new Hashtable<>();

        initChatComponents(serverConnector, currentConv);

    }

    public JSONObject getCurrentUserInfo() {
        return userInfo;
    }

    public JSONObject getCurrerntConversationInfo() {
        return usersConversations.get(currentConversation);
    }

    public int getCurrentChatId() {
        return currentConversation;
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

    public boolean setCurrentConversation(int newCurrentConv) {
        if (!usersConversations.containsKey(newCurrentConv)) {
            return false;
        }
        currentConversation = newCurrentConv;
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
        ArrayList<JSONObject> newMessages = chatAccesor.getMessagesInConversation(currentConversation);
        messagesInUsersConversation.put(currentConversation, newMessages);
        return newMessages;
    }

    public JSONObject sendMessage(String text) {
        JSONObject newMessage = chatAccesor.sendMessage(currentConversation, userId, text)
                .getJSONObject(MessagesTopLevelConstants.VALUE.value());
        messagesInUsersConversation.get(currentConversation).add(newMessage);
        return newMessage;
    }

    public JSONObject getCurentConversationInfo() {
        return usersConversations.get(currentConversation);
    }

    public JSONObject getUserInformation(int id) {
        if (usersEncountered.keySet().contains(id)) {
            return usersEncountered.get(id);
        }
        var newUser = chatAccesor.getUserInfo(id).getJSONObject(MessagesTopLevelConstants.VALUE.value());
        usersEncountered.put(newUser.getInt(ChatMessagesConstants.USER_INFO_ID.value()), newUser);
        return newUser;
    }

    public JSONObject getUserInformation(String username) {
        return chatAccesor.getUserInfo(username).getJSONObject(MessagesTopLevelConstants.VALUE.value());
    }

    public JSONObject createConversation(String name, ArrayList<String> usernames) {
        JSONObject conversation_info = chatAccesor.addConversation(name, usernames)
                .getJSONObject(MessagesTopLevelConstants.VALUE.value());
        usersConversations.put(conversation_info.getInt(ChatMessagesConstants.CONVERSATION_ID.value()),
                conversation_info);
        return conversation_info;
    }

    public JSONObject addUsersToConversation(String conversationName, ArrayList<String> usernames) {
        int conversationId = getConversationIdUsingName(conversationName);
        if (conversationId == -1) {
            return new JSONObject("{\"outcome\":false}");
        }
        if (usersInConversarion.get(conversationId) == null) {
            getUsersInConversation(conversationId);
        }
        for (String username : usernames) {
            JSONObject user_info = chatAccesor.getUserInfo(username)
                    .getJSONObject(MessagesTopLevelConstants.VALUE.value());
            usersInConversarion.get(conversationId).add(user_info.getInt(ChatMessagesConstants.USER_INFO_ID.value()));
        }
        return chatAccesor.addUsersToConversation(conversationId, usernames)
                .getJSONObject(MessagesTopLevelConstants.VALUE.value());
    }

    public JSONObject addUsersToCurrentConversation(ArrayList<String> usernames) {
        return addUsersToConversation(
                usersConversations.get(currentConversation).getString(ChatMessagesConstants.CONVERSATION_NAME.value()),
                usernames);
    }

    public Hashtable<String, Integer> getConversationsNamesToIds() {
        Hashtable<String, Integer> conv = new Hashtable<>();
        for (int id_key : usersConversations.keySet()) {
            conv.put(usersConversations.get(id_key).getString(ChatMessagesConstants.CONVERSATION_NAME.value()), id_key);
        }
        return conv;
    }

    public ArrayList<Integer> getUsersInCurrentConversation() {
        return getUsersInConversation(currentConversation);
    }

    private ArrayList<Integer> getUsersInConversation(int conversation) {
        if (usersInConversarion.containsKey(conversation)) {
            return usersInConversarion.get(conversation);
        }

        ArrayList<Integer> usersInConv = chatAccesor.getUsersInConversation(conversation);
        usersInConversarion.put(conversation, usersInConv);
        return usersInConv;
    }

    public int userId() {
        return userId;
    }

    public void updateStatus() {
        if (usersConversations.size() == 0) {
            return;
        }
        JSONObject conversations = chatAccesor.getUsersConversations(userId);
        putConversationsResponseToHashtable(conversations);

        int latest_msg = 1;
        if (messagesInUsersConversation.get(currentConversation) != null
                && messagesInUsersConversation.get(currentConversation).size() != 0) {
            latest_msg = messagesInUsersConversation.get(currentConversation)
                    .get(messagesInUsersConversation.get(currentConversation).size() - 1).getInt("message_id");
        }
        JSONObject newMessages_response = chatAccesor.getNewMessagesInConverastion(currentConversation,
                latest_msg);
        for (int i = 0; i < newMessages_response.getJSONArray(MessagesTopLevelConstants.VALUE.value())
                .length(); i += 1) {
            ArrayList<JSONObject> messages = messagesInUsersConversation.get(currentConversation);
            messages.add(newMessages_response.getJSONArray(MessagesTopLevelConstants.VALUE.value()).getJSONObject(i));
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
            data = ImageProcessor.convertFilepathToBytes(path, format);
        } catch (Exception e) {
            System.out.println(e);
        }
        return chatAccesor.sendImage(currentConversation, userId, data, format);
    }

    public String getCurrentConversationCode() {
        if (usersConversations.keySet().contains(currentConversation)
                && usersConversations.get(currentConversation).has(ChatMessagesConstants.CONVERSATION_CODE.value())) {
            System.out.println(usersConversations.get(currentConversation));
            return usersConversations.get(currentConversation)
                    .getString(ChatMessagesConstants.CONVERSATION_CODE.value());
        }
        if (currentConversation != -1) {
            JSONObject response = chatAccesor.getConversationCode(currentConversation);
            var convCode = response.getJSONObject(MessagesTopLevelConstants.VALUE.value())
                    .getString(ChatMessagesConstants.CONVERSATION_CODE.value());
            usersConversations.get(currentConversation).put(ChatMessagesConstants.CONVERSATION_CODE.value(), convCode);
            return convCode;
        }
        return "";
    }

    public boolean joinConversationUsingCode(String code) {
        JSONObject response = chatAccesor.joinConversationUsingCode(code, userId);
        boolean outcome = response.getJSONObject(MessagesTopLevelConstants.VALUE.value())
                .getBoolean(MessagesTopLevelConstants.OUTCOME.value());
        if (outcome) {
            JSONObject conversations = chatAccesor.getUsersConversations(userId);
            putConversationsResponseToHashtable(conversations);
        }
        return outcome;
    }

    public void addExternalMessage(JSONObject message) {
        if (messagesInUsersConversation.keySet().contains(message.getInt(ChatMessagesConstants.CONVERSATION_ID.value()))
                && message.getInt(ChatMessagesConstants.MESSAGE_SENDER_ID.value()) != userId) {
            messagesInUsersConversation.get(message.getInt(ChatMessagesConstants.CONVERSATION_ID.value())).add(message);
        }
    }

    public String changeCurrentConversationName(String newName) {
        JSONObject response = chatAccesor.changeConversationName(currentConversation, newName);
        if (response.getJSONObject(MessagesTopLevelConstants.VALUE.value())
                .getBoolean(MessagesTopLevelConstants.OUTCOME.value())) {
            usersConversations.get(currentConversation).put(ChatMessagesConstants.CONVERSATION_NAME.value(), newName);
            return newName;
        }
        return usersConversations.get(currentConversation).getString(ChatMessagesConstants.CONVERSATION_NAME.value());
    }

    public Boolean removeUserFromCurrentConversation(String username) {
        int userId = findUsersId(username);
        if (userId == -1) {
            return false;
        }
        JSONObject result = chatAccesor.RemoveUserFromConversation(currentConversation, userId);
        if (result.getJSONObject(MessagesTopLevelConstants.VALUE.value())
                .getBoolean(MessagesTopLevelConstants.OUTCOME.value())) {
            usersInConversarion.get(currentConversation).remove(userId);
        }
        return result.getJSONObject(MessagesTopLevelConstants.VALUE.value())
                .getBoolean(MessagesTopLevelConstants.OUTCOME.value());
    }

    private int findUsersId(String username) {
        for (int userId : usersEncountered.keySet()) {
            if (usersEncountered.get(userId).getString(ChatMessagesConstants.USERNAME.value()).equals(username)) {
                return userId;
            }
        }
        return -1;
    }

    private void initChatComponents(ServerConnector serverConnector, int currentConv) {
        usersEncountered.put(this.userId, this.userInfo);
        chatAccesor = new ChatAccesors(serverConnector);
        putConversationsResponseToHashtable(chatAccesor.getUsersConversations(userId));
        currentConversation = currentConv;
        if (currentConversation == -1 && usersConversations.size() != 0) {
            currentConversation = Collections.min(usersConversations.keySet());
        }
        getCurrentMessages();
        getUsersInConversation(currentConversation);
    }

    private void putConversationsResponseToHashtable(JSONObject response) {
        usersConversations.clear();
        JSONArray conversations = response.getJSONArray(MessagesTopLevelConstants.VALUE.value());
        for (int i = 0; i < conversations.length(); i += 1) {
            usersConversations.put(conversations.getJSONObject(i).getInt(ChatMessagesConstants.CONVERSATION_ID.value()),
                    conversations.getJSONObject(i));
        }
    }

    private int getConversationIdUsingName(String conversationName) {
        for (Integer key : usersConversations.keySet()) {
            if (usersConversations.get(key).getString(ChatMessagesConstants.CONVERSATION_NAME.value())
                    .equals(conversationName)) {
                return key;
            }
        }
        return -1;
    }

}
