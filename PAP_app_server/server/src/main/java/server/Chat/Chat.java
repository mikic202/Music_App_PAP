package server.Chat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import server.Main;
import server.DatabaseInteractors.ConversationDataAccesor;
import server.DatabaseInteractors.ConversationDataSetter;
import server.DatabaseInteractors.ConversationDatabsaeInformation;
import server.DatabaseInteractors.MessageDataAccesor;
import server.DatabaseInteractors.MessageDataSetter;
import server.DatabaseInteractors.UserDataAccesor;
import server.DatabaseInteractors.UserDataSetter;
import server.DatabaseInteractors.UserDatabaseInformation;

public class Chat {

    static final String[] CONVERSATION_CODE_KEYS = { "ocvdt&zvfl", "KzA#biPnxc", "*G$favyhmo", "!bs?j@iztq" };

    public static JSONObject procesRequests(RequestTypes reqType, JSONObject request) {
        try {
            return _generateResponse(reqType, request);
        } catch (Exception e) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("type", reqType.value());
            JSONObject errorJson = new JSONObject(String.format("{ \"outcome\":false}"));
            errorJson.put("error", e);
            jsonResponse.put("value", errorJson);
            return jsonResponse;

        }
    }

    private static JSONObject _getMessegesInConversation(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> messages = ConversationDataAccesor
                .getMessagesInConversation(request.getInt("conversation_id"));
        for (Integer messageId : messages) {
            response.add(MessageDataAccesor.getData(messageId));
        }
        return _convertResponseToJson(response, RequestTypes.GET_MESSAGES);
    }

    private static JSONObject _getUsersConversations(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> conversations = UserDataAccesor.getUserConversations(request.getInt("user_id"));
        System.out.println(conversations);
        for (Integer conversationId : conversations) {
            response.add(ConversationDataAccesor.getData(conversationId));
        }
        return _convertResponseToJson(response, RequestTypes.GET_USERS_CONVERSATIONS);
    }

    private static JSONObject _sendMessage(JSONObject request) {
        int addedMsg = _putMessageInDatabase(request);
        Hashtable<String, String> data = MessageDataAccesor.getData(addedMsg);
        Main.updater.sendMessage(addedMsg, data.get("conversation_id"));
        return _convertResponseToJson(data, RequestTypes.GET_MESSAGES);
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
        int numberOfUsers = request.getJSONArray("users").length();
        int newConversation = _addConversation(name, numberOfUsers);
        ArrayList<Integer> users = new ArrayList<>();
        for (int i = 0; i < request.getJSONArray("users").length(); i++) {
            users.add(request.getJSONArray("users").getInt(i));
        }
        _addUsersToConversation(users, newConversation);
        return _convertResponseToJson(ConversationDataAccesor.getData(newConversation),
                RequestTypes.CREATE_CONVERSATION);
    }

    private static int _addConversation(String name, int numberOfUsers) {
        Hashtable<String, String> data = new Hashtable<>();
        data.put("name", name);
        data.put("number_of_users", Integer.toString(numberOfUsers));
        return ConversationDataSetter.addData(data);
    }

    private static void _addUsersToConversation(ArrayList<Integer> users, int conversationId) {
        for (int user : users) {
            var usersConversations = UserDataAccesor.getUserConversations(user);
            if (!usersConversations.contains(conversationId)) {
                UserDataSetter.addUserToConversation(conversationId, user);
            }
        }
    }

    private static JSONObject _processAddUsersToConversation(JSONObject request) {
        int numberOfUsers = request.getJSONArray("users").length();
        int conversationId = request.getInt("conversation_id");
        ArrayList<Integer> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            System.out.println(UserDataAccesor.getUserConversations(request.getJSONArray("users").getInt(i)));
            if (!UserDataAccesor.getUserConversations(request.getJSONArray("users").getInt(i))
                    .contains(conversationId)) {
                users.add(request.getJSONArray("users").getInt(i));
            } else {
                numberOfUsers -= 1;
            }

        }
        System.out.println(users);
        _addUsersToConversation(users, conversationId);
        Hashtable<String, String> previousData = ConversationDataAccesor.getData(conversationId);
        previousData.put(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value(),
                Integer.toString(Integer
                        .parseInt(previousData.get(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value()))
                        + numberOfUsers));
        ConversationDataSetter.setData(conversationId, previousData);
        Hashtable<String, String> outcome = new Hashtable<>();
        outcome.put("outcome", "true");
        return _convertResponseToJson(outcome, RequestTypes.ADD_USER_TO_CONVERSATION);
    }

    private static JSONObject _convertResponseToJson(Hashtable<String, String> response, RequestTypes reqType) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("type", reqType.value());
        JSONObject jsonResponseValue = new JSONObject();
        Set<String> keys = response.keySet();
        for (String key : keys) {
            jsonResponseValue.put(key, response.get(key));
        }
        jsonResponse.put("value", jsonResponseValue);
        return jsonResponse;
    }

    private static JSONObject _checkUsersInfo(JSONObject request) {
        if (request.getString("type").equals("username")) {
            Hashtable<String, String> hashResponse = UserDataAccesor
                    .getData(UserDatabaseInformation.USERNAME_COLUMN.value(), request.getString("username"));
            hashResponse.remove("password");
            return _convertResponseToJson(hashResponse, RequestTypes.USER_INFO);
        }
        Hashtable<String, String> hashResponse = UserDataAccesor
                .getData(UserDatabaseInformation.ID_COLUMN.value(), request.getInt("user_id"));
        hashResponse.remove("password");
        return _convertResponseToJson(hashResponse, RequestTypes.USER_INFO);
    }

    private static JSONObject _convertResponseToJson(ArrayList<Hashtable<String, String>> response,
            RequestTypes reqType) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("type", reqType.value());
        JSONArray jsonResponseValue = new JSONArray();
        for (Hashtable<String, String> element : response) {
            Set<String> keys = element.keySet();
            JSONObject jsonElement = new JSONObject();
            for (String key : keys) {
                jsonElement.put(key, element.get(key));
            }
            jsonResponseValue.put(jsonElement);
        }
        jsonResponse.put("value", jsonResponseValue);
        return jsonResponse;
    }

    private static JSONObject _getUsersInConversation(JSONObject request) {
        ArrayList<Integer> users = ConversationDataAccesor.getUsersInConversation(request.getInt("conversation_id"));
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("type", RequestTypes.GET_USERS_CONVERSATIONS.value());
        JSONArray jsonUsers = new JSONArray();
        for (Integer user : users) {
            jsonUsers.put(user);
        }
        jsonResponse.put("value", jsonUsers);
        return jsonResponse;
    }

    private static JSONObject _getNewMessagessInConversation(JSONObject request) {
        ArrayList<Hashtable<String, String>> messages = MessageDataAccesor
                .getMessagesOlderThanGiven(request.getInt("latest_message"), request.getInt("conversation_id"));
        return _convertResponseToJson(messages, RequestTypes.GET_LATEST_MESSAGE);
    }

    private static JSONObject _processSendImage(JSONObject request) {
        request.put("text", (request.getJSONArray("image")).toString());
        int newMessage = _putMessageInDatabase(request);

        MessageDataSetter.setIsImage(newMessage);
        return _convertResponseToJson(MessageDataAccesor.getData(newMessage), RequestTypes.SEND_IMAGE);
    }

    private static String _generateConversationCode(int conversationId) {

        var index = (int) (Math.random() * CONVERSATION_CODE_KEYS.length);

        String chosenKeytable = CONVERSATION_CODE_KEYS[index];
        int convertableId = conversationId;
        String conversationCode = Integer.toString(index);
        do {
            conversationCode += chosenKeytable.charAt(convertableId % 10);
            convertableId = convertableId / 10;
        } while (convertableId > 0);
        return conversationCode;
    }

    private static JSONObject _processGetConversationCode(JSONObject request) {
        String code = _generateConversationCode(request.getInt("conversation_id"));
        JSONObject response = new JSONObject();
        JSONObject responseValue = new JSONObject();
        responseValue.put("conversation code", code);
        response.put("value", responseValue);
        response.put("type", RequestTypes.GET_CONVERSATION_CODE.value());
        return response;
    }

    private static JSONObject _procesJoinConversationUsingCode(JSONObject request) {
        int conversationId = _decodeConversationCode(request.getString("conversation_code"));
        ArrayList<Integer> users = new ArrayList<>();
        users.add(request.getInt("user_id"));
        System.out.println(users);
        _addUsersToConversation(users, conversationId);
        JSONObject responseValue = new JSONObject();
        var usersConversations = UserDataAccesor.getUserConversations(users.get(0));
        responseValue.put("outcome", usersConversations.contains(conversationId));
        if (usersConversations.contains(conversationId)) {
            responseValue.put("conversation_id", conversationId);
        }
        JSONObject response = new JSONObject();
        response.put("value", responseValue);
        response.put("type", RequestTypes.JOIN_CONVERSATION_WITH_CODE.value());
        return response;
    }

    private static int _decodeConversationCode(String code) {
        String chosenKeytable = CONVERSATION_CODE_KEYS[Character.getNumericValue(code.charAt(0))];
        String conversationCode = new String();

        for (int i = 1; i < code.length(); i++) {
            conversationCode = chosenKeytable.indexOf(code.charAt(i), 0) + conversationCode;
        }
        return Integer.parseInt(conversationCode);

    }

    private static JSONObject _generateResponse(RequestTypes reqType, JSONObject request) {
        JSONObject response = new JSONObject();
        switch (reqType) {
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
            case GET_CONVERSATION_CODE:
                response = _processGetConversationCode(request);
                break;
            case JOIN_CONVERSATION_WITH_CODE:
                response = _procesJoinConversationUsingCode(request);
                break;

        }
        return response;
    }

}
