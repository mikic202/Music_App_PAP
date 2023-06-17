package client.Chat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.ServerConnector.ServerConnector;

import java.awt.Color;
import java.awt.Image;
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
            usersInConversarion.get(conversationId).put(user_info.getInt(ChatMessagesConstants.USER_INFO_ID.value()),
                    user_info);
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

    public Hashtable<Integer, JSONObject> getUsersInCurrentConversation() {
        return getUsersInConversation(currentConversation);
    }

    private Hashtable<Integer, JSONObject> getUsersInConversation(int conversation) {
        if (usersInConversarion.containsKey(conversation)) {
            return usersInConversarion.get(conversation);
        }
        Hashtable<Integer, JSONObject> users_in_conv = new Hashtable<Integer, JSONObject>();
        JSONArray users = chatAccesor.getUsersInConversation(conversation)
                .getJSONArray(MessagesTopLevelConstants.VALUE.value());
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
            BufferedImage imageBuff = convertFilepathToBufferedImage(path);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, format, buffer);

            data = buffer.toByteArray();
        } catch (Exception e) {
            System.out.println(e);
        }
        return chatAccesor.sendImage(currentConversation, userId, data, format);
    }

    public String getConversationCode() {
        if (currentConversation != -1) {
            JSONObject response = chatAccesor.getConversationCode(currentConversation);
            return response.getJSONObject(MessagesTopLevelConstants.VALUE.value()).getString("conversation code");
        }
        return "";
    }

    public boolean joinConversationUsingCode(String code) {
        JSONObject response = chatAccesor.joinConversationUsingCode(code, userId);
        boolean outcome = response.getJSONObject(MessagesTopLevelConstants.VALUE.value())
                .getBoolean(MessagesTopLevelConstants.OUTCOME.value());
        if (outcome) {
            JSONObject conversations = chatAccesor.getUsersConversations(userId);
            convertConversationsResponseToHashtable(conversations);
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
        convertConversationsResponseToHashtable(chatAccesor.getUsersConversations(userId));
        currentConversation = currentConv;
        if (currentConversation == -1 && usersConversations.size() != 0) {
            currentConversation = Collections.min(usersConversations.keySet());
        }
        getCurrentMessages();
        getUsersInConversation(currentConversation);
    }

    private BufferedImage convertFilepathToBufferedImage(String path) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        Double imageHeight = 0.0;
        Double imageWidth = 0.0;
        if (img.getWidth() < 100.0 && img.getHeight() < 100) {

        } else if (img.getWidth() > img.getHeight()) {
            imageWidth = 100.0;
            imageHeight = img.getHeight() * (100.0 / img.getWidth());
        } else {
            imageHeight = 100.0;
            imageWidth = img.getWidth() * (100.0 / img.getHeight());
        }

        Image scaledImage = img.getScaledInstance(imageWidth.intValue(), imageHeight.intValue(),
                Image.SCALE_SMOOTH);
        BufferedImage imageBuff = new BufferedImage(imageWidth.intValue(), imageHeight.intValue(),
                BufferedImage.TYPE_INT_RGB);
        imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);
        return imageBuff;
    }

    private void convertConversationsResponseToHashtable(JSONObject response) {
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
