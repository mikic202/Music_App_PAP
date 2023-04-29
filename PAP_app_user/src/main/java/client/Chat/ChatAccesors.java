package client.Chat;

import java.util.ArrayList;
import org.json.JSONObject;

import client.ServerConnector.ServerConnector;

public class ChatAccesors {
    public ChatAccesors(ServerConnector server_connector) {
        this.server_connector = server_connector;
    }

    public JSONObject sendMessage(int conversation_id, int sender_id, String text) {
        JSONObject procesed_request = RequestCreator.createSendMsgRequest(conversation_id, sender_id, text);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject addConversation(String name, ArrayList<String> usernames) {
        ArrayList<Integer> users_ids = getUsersIds(usernames);
        JSONObject procesed_request = RequestCreator.createAddRonversationRequest(name, users_ids);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    private ArrayList<Integer> getUsersIds(ArrayList<String> usernames) {
        ArrayList<Integer> users_ids = new ArrayList<>();
        for (String user : usernames) {
            JSONObject user_info = getUserInfo(user).getJSONObject("value");
            if (user_info.has("ID")) {
                users_ids.add(user_info.getInt("ID"));
            }
        }
        return users_ids;
    }

    public JSONObject getMessagesInConversation(int conversation_id) {
        JSONObject procesed_request = RequestCreator.createGetMessagesRequest(conversation_id);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject getUsersConversations(int user_id) {
        JSONObject procesed_request = RequestCreator.createGetConversationsRequest(user_id);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject addUsersToConversation(int conversation_id, ArrayList<String> usernames) {
        ArrayList<Integer> users_ids = getUsersIds(usernames);
        JSONObject procesed_request = RequestCreator.createAddUserToConversationRequest(conversation_id,
                users_ids);
        JSONObject response = server_connector.send_request(procesed_request);
        return response;
    }

    public JSONObject getUserInfo(int user_id) {
        JSONObject request = RequestCreator.createGetUserInformationRequest(user_id);
        JSONObject response = server_connector.send_request(request);
        return response;
    }

    public JSONObject getUserInfo(String username) {
        JSONObject request = RequestCreator.createGetUserInformationRequest(username);
        JSONObject response = server_connector.send_request(request);
        return response;
    }

    public JSONObject getUsersInConversation(int conversation_id) {
        JSONObject request = RequestCreator.getUsersInConversation(conversation_id);
        JSONObject response = server_connector.send_request(request);
        return response;
    }

    public JSONObject getNewMessagesInConverastion(int conversation, Integer latest_message) {
        JSONObject request = RequestCreator.createGetNewMessagesInConversation(conversation, latest_message);
        JSONObject response = server_connector.send_request(request);
        return response;
    }

    private ServerConnector server_connector;

}