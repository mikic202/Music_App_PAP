package client.Chat;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.ServerConnector.ServerConnector;

public class ChatAccesors {
    public ChatAccesors(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    public JSONObject sendMessage(int conversationId, int senderId, String text) {
        JSONObject procesedRequest = ChatRequestCreator.createSendMsgRequest(conversationId, senderId, text);
        JSONObject response = serverConnector.sendRequest(procesedRequest);
        return response;
    }

    public JSONObject addConversation(String name, ArrayList<String> usernames) {
        ArrayList<Integer> usersIds = getUsersIds(usernames);
        JSONObject procesedRequest = ChatRequestCreator.createAddRonversationRequest(name, usersIds);
        JSONObject response = serverConnector.sendRequest(procesedRequest);
        return response;
    }

    private ArrayList<Integer> getUsersIds(ArrayList<String> usernames) {
        ArrayList<Integer> usersIds = new ArrayList<>();
        for (String user : usernames) {
            JSONObject user_info = getUserInfo(user).getJSONObject(MessagesTopLevelConstants.VALUE.value());
            if (user_info.has(ChatMessagesConstants.USER_INFO_ID.value())) {
                usersIds.add(user_info.getInt(ChatMessagesConstants.USER_INFO_ID.value()));
            }
        }
        return usersIds;
    }

    public ArrayList<JSONObject> getMessagesInConversation(int conversationId) {
        JSONObject procesedRequest = ChatRequestCreator.createGetMessagesRequest(conversationId);
        JSONObject response = serverConnector.sendRequest(procesedRequest);
        ArrayList<JSONObject> newMessages = new ArrayList<>();
        for (int i = 0; i < response.getJSONArray(MessagesTopLevelConstants.VALUE.value()).length(); i += 1) {
            newMessages.add(response.getJSONArray(MessagesTopLevelConstants.VALUE.value()).getJSONObject(i));
        }
        return newMessages;
    }

    public JSONObject getUsersConversations(int userId) {
        JSONObject procesedRequest = ChatRequestCreator.createGetConversationsRequest(userId);
        JSONObject response = serverConnector.sendRequest(procesedRequest);
        return response;
    }

    public JSONObject addUsersToConversation(int conversationId, ArrayList<String> usernames) {
        ArrayList<Integer> usersIds = getUsersIds(usernames);
        JSONObject procesedRequest = ChatRequestCreator.createAddUserToConversationRequest(conversationId,
                usersIds);
        JSONObject response = serverConnector.sendRequest(procesedRequest);
        return response;
    }

    public JSONObject getUserInfo(int userId) {
        JSONObject request = ChatRequestCreator.createGetUserInformationRequest(userId);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject getUserInfo(String username) {
        JSONObject request = ChatRequestCreator.createGetUserInformationRequest(username);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public ArrayList<Integer> getUsersInConversation(int conversationId) {
        JSONObject request = ChatRequestCreator.getUsersInConversation(conversationId);
        JSONObject response = serverConnector.sendRequest(request);
        ArrayList<Integer> usersInConv = new ArrayList<>();
        JSONArray users = response
                .getJSONArray(MessagesTopLevelConstants.VALUE.value());
        for (int i = 0; i < users.length(); i += 1) {
            usersInConv.add(users.getInt(i));
        }
        return usersInConv;
    }

    public JSONObject getNewMessagesInConverastion(int conversation, int latestMessage) {
        JSONObject request = ChatRequestCreator.createGetNewMessagesInConversation(conversation, latestMessage);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject sendImage(int conversationId, int senderId, byte[] image, String format) {
        JSONObject request = ChatRequestCreator.createSendImageRequest(conversationId, senderId, image, format);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject getConversationCode(int conversationId) {
        JSONObject request = ChatRequestCreator.createGetConversationCodeRequest(conversationId);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject joinConversationUsingCode(String code, int userId) {
        JSONObject request = ChatRequestCreator.createJoinConversationUsingCodeRequest(code, userId);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject changeConversationName(int conversationId, String newName) {
        JSONObject request = ChatRequestCreator.createChangeConversationNameRequest(conversationId, newName);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject RemoveUserFromConversation(int conversationId, int userTd) {
        JSONObject request = ChatRequestCreator.createRemoveUserFromConversationRequest(conversationId, userTd);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    private ServerConnector serverConnector;

}