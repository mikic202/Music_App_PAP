package client.Chat;

import java.util.ArrayList;
import org.json.JSONObject;

import client.ServerConnector.ServerConnector;

public class ChatAccesors {
    public ChatAccesors(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    public JSONObject sendMessage(int conversationId, int senderId, String text) {
        JSONObject procesedRequest = RequestCreator.createSendMsgRequest(conversationId, senderId, text);
        JSONObject response = serverConnector.sendRequest(procesedRequest);
        return response;
    }

    public JSONObject addConversation(String name, ArrayList<String> usernames) {
        ArrayList<Integer> usersIds = getUsersIds(usernames);
        JSONObject procesedRequest = RequestCreator.createAddRonversationRequest(name, usersIds);
        JSONObject response = serverConnector.sendRequest(procesedRequest);
        return response;
    }

    private ArrayList<Integer> getUsersIds(ArrayList<String> usernames) {
        ArrayList<Integer> usersIds = new ArrayList<>();
        for (String user : usernames) {
            JSONObject user_info = getUserInfo(user).getJSONObject("value");
            if (user_info.has("ID")) {
                usersIds.add(user_info.getInt("ID"));
            }
        }
        return usersIds;
    }

    public JSONObject getMessagesInConversation(int conversationId) {
        JSONObject procesedRequest = RequestCreator.createGetMessagesRequest(conversationId);
        JSONObject response = serverConnector.sendRequest(procesedRequest);
        return response;
    }

    public JSONObject getUsersConversations(int userId) {
        JSONObject procesedRequest = RequestCreator.createGetConversationsRequest(userId);
        JSONObject response = serverConnector.sendRequest(procesedRequest);
        return response;
    }

    public JSONObject addUsersToConversation(int conversationId, ArrayList<String> usernames) {
        ArrayList<Integer> usersIds = getUsersIds(usernames);
        JSONObject procesedRequest = RequestCreator.createAddUserToConversationRequest(conversationId,
                usersIds);
        JSONObject response = serverConnector.sendRequest(procesedRequest);
        return response;
    }

    public JSONObject getUserInfo(int userId) {
        JSONObject request = RequestCreator.createGetUserInformationRequest(userId);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject getUserInfo(String username) {
        JSONObject request = RequestCreator.createGetUserInformationRequest(username);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject getUsersInConversation(int conversationId) {
        JSONObject request = RequestCreator.getUsersInConversation(conversationId);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject getNewMessagesInConverastion(int conversation, int latestMessage) {
        JSONObject request = RequestCreator.createGetNewMessagesInConversation(conversation, latestMessage);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject sendImage(int conversationId, int senderId, byte[] image, String format) {
        JSONObject request = RequestCreator.createSendImageRequest(conversationId, senderId, image, format);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject getConversationCode(int conversationId) {
        JSONObject request = RequestCreator.createGetConversationCodeRequest(conversationId);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject joinConversationUsingCode(String code, int userId) {
        JSONObject request = RequestCreator.createJoinConversationUsingCodeRequest(code, userId);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject changeConversationName(int conversationId, String newName) {
        JSONObject request = RequestCreator.createChangeConversationNameRequest(conversationId, newName);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    public JSONObject RemoveUserFromConversation(int conversationId, int userTd) {
        JSONObject request = RequestCreator.createRemoveUserFromConversationRequest(conversationId, userTd);
        JSONObject response = serverConnector.sendRequest(request);
        return response;
    }

    private ServerConnector serverConnector;

}