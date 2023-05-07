package server.Chat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import server.DatabaseInteractors.ConversationDataAccesor;
import server.DatabaseInteractors.ConversationDataSetter;
import server.DatabaseInteractors.ConversationDatabsaeInformation;
import server.DatabaseInteractors.MessageDataAccesor;
import server.DatabaseInteractors.MessageDataSetter;
import server.DatabaseInteractors.UserDataAccesor;
import server.DatabaseInteractors.UserDataSetter;
import server.DatabaseInteractors.UserDatabaseInformation;

public class Chat {
    public static JSONObject procesRequests(RequestTypes req_type, JSONObject request) {
        try {
            return _generateResponse(req_type, request);
        } catch (Exception e) {
            JSONObject json_response = new JSONObject();
            json_response.put("type", req_type.value());
            JSONObject new_json = new JSONObject(
                    String.format("{\"error\": \"%s\", \"outcome\":false}",
                            "there is something incorrect with your request"));
            json_response.put("value",
                    new_json);
            return json_response;

        }
    }

    private static JSONObject _getMessegesInConversation(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> messages = ConversationDataAccesor
                .getMessagesInConversation(request.getInt("conversation_id"));
        for (Integer message_id : messages) {
            response.add(MessageDataAccesor.getData(message_id));
        }
        return _convertResponseToJson(response, RequestTypes.GET_MESSAGES);
    }

    private static JSONObject _getUsersConversations(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> conversations = UserDataAccesor.getUserConversations(request.getInt("user_id"));
        for (Integer conversation_id : conversations) {
            response.add(ConversationDataAccesor.getData(conversation_id));
        }
        return _convertResponseToJson(response, RequestTypes.GET_USERS_CONVERSATIONS);
    }

    private static JSONObject _sendMessage(JSONObject request) {
        int added_msg = _putMessageInDatabase(request);
        return _convertResponseToJson(MessageDataAccesor.getData(added_msg), RequestTypes.GET_MESSAGES);
    }

    public static int _putMessageInDatabase(JSONObject request) {
        Hashtable<String, String> data = new Hashtable<>();
        data.put("sender", Integer.toString(request.getInt("sender_id")));
        data.put("conversation", Integer.toString(request.getInt("conversation_id")));
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        data.put("send_date", timestamp.toString());
        data.put("text", request.getString("text"));
        return MessageDataSetter.addData(data);
    }

    private static JSONObject _createConversation(JSONObject request) {
        String name = request.getString("name");
        int number_of_users = request.getJSONArray("users").length();
        int new_conversation = _addConversation(name, number_of_users);
        ArrayList<Integer> users = new ArrayList<>();
        for (int i = 0; i < request.getJSONArray("users").length(); i++) {
            users.add(request.getJSONArray("users").getInt(i));
        }
        _addUsersToConversation(users, new_conversation);
        return _convertResponseToJson(ConversationDataAccesor.getData(new_conversation),
                RequestTypes.CREATE_CONVERSATION);
    }

    private static int _addConversation(String name, int number_of_users) {
        Hashtable<String, String> data = new Hashtable<>();
        data.put("name", name);
        data.put("number_of_users", Integer.toString(number_of_users));
        return ConversationDataSetter.addData(data);
    }

    private static void _addUsersToConversation(ArrayList<Integer> users, int conversation_id) {
        for (int user : users) {
            UserDataSetter.addUserToConversation(conversation_id, user);
        }
    }

    private static JSONObject _processAddUsersToConversation(JSONObject request) {
        int number_of_users = request.getJSONArray("users").length();
        int conversation_id = request.getInt("conversation_id");
        ArrayList<Integer> users = new ArrayList<>();
        for (int i = 0; i < number_of_users; i++) {
            System.out.println(UserDataAccesor.getUserConversations(request.getJSONArray("users").getInt(i)));
            if (!UserDataAccesor.getUserConversations(request.getJSONArray("users").getInt(i))
                    .contains(conversation_id)) {
                users.add(request.getJSONArray("users").getInt(i));
            } else {
                number_of_users -= 1;
            }

        }
        System.out.println(users);
        _addUsersToConversation(users, conversation_id);
        Hashtable<String, String> previous_data = ConversationDataAccesor.getData(conversation_id);
        previous_data.put(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value(),
                Integer.toString(Integer
                        .parseInt(previous_data.get(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value()))
                        + number_of_users));
        ConversationDataSetter.setData(conversation_id, previous_data);
        Hashtable<String, String> outcome = new Hashtable<>();
        outcome.put("outcome", "true");
        return _convertResponseToJson(outcome, RequestTypes.ADD_USER_TO_CONVERSATION);
    }

    private static JSONObject _convertResponseToJson(Hashtable<String, String> response, RequestTypes req_type) {
        JSONObject json_response = new JSONObject();
        json_response.put("type", req_type.value());
        JSONObject json_response_value = new JSONObject();
        Set<String> keys = response.keySet();
        for (String key : keys) {
            json_response_value.put(key, response.get(key));
        }
        json_response.put("value", json_response_value);
        return json_response;
    }

    private static JSONObject _checkUsersInfo(JSONObject request) {
        if (request.getString("type").equals("username")) {
            Hashtable<String, String> hash_response = UserDataAccesor
                    .getData(UserDatabaseInformation.USERNAME_COLUMN.value(), request.getString("username"));
            hash_response.remove("password");
            return _convertResponseToJson(hash_response, RequestTypes.USER_INFO);
        }
        Hashtable<String, String> hash_response = UserDataAccesor
                .getData(UserDatabaseInformation.ID_COLUMN.value(), request.getInt("user_id"));
        hash_response.remove("password");
        return _convertResponseToJson(hash_response, RequestTypes.USER_INFO);
    }

    private static JSONObject _convertResponseToJson(ArrayList<Hashtable<String, String>> response,
            RequestTypes req_type) {
        JSONObject json_response = new JSONObject();
        json_response.put("type", req_type.value());
        JSONArray json_response_value = new JSONArray();
        for (Hashtable<String, String> element : response) {
            Set<String> keys = element.keySet();
            JSONObject json_element = new JSONObject();
            for (String key : keys) {
                json_element.put(key, element.get(key));
            }
            json_response_value.put(json_element);
        }
        json_response.put("value", json_response_value);
        return json_response;
    }

    private static JSONObject _getUsersInConversation(JSONObject request) {
        ArrayList<Integer> users = ConversationDataAccesor.getUsersInConversation(request.getInt("conversation_id"));
        JSONObject json_response = new JSONObject();
        json_response.put("type", RequestTypes.GET_USERS_CONVERSATIONS.value());
        JSONArray json_users = new JSONArray();
        for (Integer user : users) {
            json_users.put(user);
        }
        json_response.put("value", json_users);
        return json_response;
    }

    private static JSONObject _getNewMessagessInConversation(JSONObject request) {
        ArrayList<Hashtable<String, String>> messages = MessageDataAccesor
                .getMessagesOlderThanGiven(request.getInt("latest_message"), request.getInt("conversation_id"));
        JSONObject json_response = new JSONObject();
        return _convertResponseToJson(messages, RequestTypes.GET_LATEST_MESSAGE);
    }

    private static JSONObject _processSendImage(JSONObject request) {
        // TODO add new field to sent back message and test it
        request.put("text", (request.getJSONArray("image")).toString());
        int newMessage = _putMessageInDatabase(request);

        MessageDataSetter.setIsImage(newMessage);
        return _convertResponseToJson(MessageDataAccesor.getData(newMessage), RequestTypes.SEND_IMAGE);
    }

    private static JSONObject _generateResponse(RequestTypes req_type, JSONObject request) {
        JSONObject response = new JSONObject();
        switch (req_type) {
            case GET_MESSAGES:
                response = _getMessegesInConversation(request);
                break;
            case GET_USERS_CONVERSATIONS:
                response = _getUsersConversations(request);
                break;
            case SEND_MESSAGE:
                response = _sendMessage(request);
                break;
            case CREATE_CONVERSATION:
                response = _createConversation(request);
                break;
            case ADD_USER_TO_CONVERSATION:
                response = _processAddUsersToConversation(request);
                break;
            case USER_INFO:
                response = _checkUsersInfo(request);
                break;
            case GET_USERS_IN_CONVERSATION:
                response = _getUsersInConversation(request);
                break;
            case GET_LATEST_MESSAGE:
                response = _getNewMessagessInConversation(request);
                break;
            case SEND_IMAGE:
                response = _processSendImage(request);
                break;

        }
        return response;
    }

}
