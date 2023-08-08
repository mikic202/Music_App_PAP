package server.Chat;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import server.DatabaseInteractors.ConversationDataAccesor;
import server.DatabaseInteractors.ConversationDataSetter;
import server.DatabaseInteractors.ConversationDatabsaeInformation;
import server.DatabaseInteractors.UserDataAccesor;
import server.DatabaseInteractors.UserDataSetter;
import server.DatabaseInteractors.UserDatabaseInformation;
import server.ServerConnectionConstants.ChatMessagesConstants;
import server.ServerConnectionConstants.LoggingMessagesConstants;
import server.ServerConnectionConstants.MessagesTopLevelConstants;

public class Chat {

    static final String[] CONVERSATION_CODE_KEYS = { "ocvdt&zvfl", "KzA#biPnxc", "*G$favyhmo", "!bs?j@iztq" };

    public static JSONObject procesRequests(RequestTypes reqType, JSONObject request) {
        try {
            return _generateResponse(reqType, request);
        } catch (Exception e) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put(MessagesTopLevelConstants.TYPE.value(), reqType.value());
            JSONObject errorJson = new JSONObject(String.format("{ \"outcome\":false}"));
            errorJson.put("error", e);
            jsonResponse.put(MessagesTopLevelConstants.VALUE.value(), errorJson);
            return jsonResponse;

        }
    }

    private static JSONObject _generateResponse(RequestTypes reqType, JSONObject request) {
        JSONObject response = new JSONObject();
        switch (reqType) {
            case GET_MESSAGES:
                response = ChatMessagesHandler.getMessegesInConversation(request);
                break;
            case GET_USERS_CONVERSATIONS:
                response = _getUsersConversations(request);
                break;
            case SEND_MESSAGE:
                response = ChatMessagesHandler.sendMessage(request);
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
                response = ChatMessagesHandler.getNewMessagessInConversation(request);
                break;
            case SEND_IMAGE:
                response = ChatMessagesHandler.processSendImage(request);
                break;
            case GET_CONVERSATION_CODE:
                response = _processGetConversationCode(request);
                break;
            case JOIN_CONVERSATION_WITH_CODE:
                response = _procesJoinConversationUsingCode(request);
                break;
            case CHANGE_CONVERSATION_NAME:
                response = _processChangeConversationName(request);
                break;
            case REMOVE_USER_FROM_CONVERSATION:
                response = _removeUserFromConversation(request);
                break;

        }
        return response;
    }

    private static JSONObject _getUsersConversations(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> conversations = UserDataAccesor
                .getUserConversations(request.getInt(ChatMessagesConstants.USER_ID.value()));
        for (Integer conversationId : conversations) {
            response.add(ConversationDataAccesor.getData(conversationId));
        }
        return JsonConverter.convertResponseToJson(response, RequestTypes.GET_USERS_CONVERSATIONS);
    }

    private static JSONObject _createConversation(JSONObject request) {
        String name = request.getString(ChatMessagesConstants.CONVERSATION_NAME.value());
        int numberOfUsers = request.getJSONArray(ChatMessagesConstants.USERS_IN_CONVERSATION.value()).length();
        int newConversation = _addConversation(name, numberOfUsers);
        ArrayList<Integer> users = new ArrayList<>();
        for (int i = 0; i < request.getJSONArray(ChatMessagesConstants.USERS_IN_CONVERSATION.value()).length(); i++) {
            users.add(request.getJSONArray(ChatMessagesConstants.USERS_IN_CONVERSATION.value()).getInt(i));
        }
        _addUsersToConversation(users, newConversation);
        return JsonConverter.convertResponseToJson(ConversationDataAccesor.getData(newConversation),
                RequestTypes.CREATE_CONVERSATION);
    }

    private static int _addConversation(String name, int numberOfUsers) {
        Hashtable<String, String> data = new Hashtable<>();
        data.put(ChatMessagesConstants.CONVERSATION_NAME.value(), name);
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
        int numberOfUsers = request.getJSONArray(ChatMessagesConstants.USERS_IN_CONVERSATION.value()).length();
        int conversationId = request.getInt(ChatMessagesConstants.CONVERSATION_ID.value());
        ArrayList<Integer> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            System.out.println(UserDataAccesor.getUserConversations(
                    request.getJSONArray(ChatMessagesConstants.USERS_IN_CONVERSATION.value()).getInt(i)));
            if (!UserDataAccesor
                    .getUserConversations(
                            request.getJSONArray(ChatMessagesConstants.USERS_IN_CONVERSATION.value()).getInt(i))
                    .contains(conversationId)) {
                users.add(request.getJSONArray(ChatMessagesConstants.USERS_IN_CONVERSATION.value()).getInt(i));
            } else {
                numberOfUsers -= 1;
            }
        }
        _addUsersToConversation(users, conversationId);
        Hashtable<String, String> previousData = ConversationDataAccesor.getData(conversationId);
        previousData.put(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value(),
                Integer.toString(Integer
                        .parseInt(previousData.get(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value()))
                        + numberOfUsers));
        ConversationDataSetter.setData(conversationId, previousData);
        Hashtable<String, String> outcome = new Hashtable<>();
        outcome.put(MessagesTopLevelConstants.OUTCOME.value(), "true");
        return JsonConverter.convertResponseToJson(outcome, RequestTypes.ADD_USER_TO_CONVERSATION);
    }

    private static JSONObject _checkUsersInfo(JSONObject request) {
        if (request.getString(MessagesTopLevelConstants.TYPE.value()).equals(ChatMessagesConstants.USERNAME.value())) {
            Hashtable<String, String> hashResponse = UserDataAccesor
                    .getData(UserDatabaseInformation.USERNAME_COLUMN.value(),
                            request.getString(ChatMessagesConstants.USERNAME.value()));
            hashResponse.remove(LoggingMessagesConstants.PASSWORD.value());
            return JsonConverter.convertResponseToJson(hashResponse, RequestTypes.USER_INFO);
        }
        Hashtable<String, String> hashResponse = UserDataAccesor
                .getData(request.getInt(ChatMessagesConstants.USER_ID.value()));
        hashResponse.remove(LoggingMessagesConstants.PASSWORD.value());
        return JsonConverter.convertResponseToJson(hashResponse, RequestTypes.USER_INFO);
    }

    private static JSONObject _getUsersInConversation(JSONObject request) {
        ArrayList<Integer> users = ConversationDataAccesor
                .getUsersInConversation(request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()));
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(MessagesTopLevelConstants.TYPE.value(), RequestTypes.GET_USERS_CONVERSATIONS.value());
        JSONArray jsonUsers = new JSONArray();
        for (Integer user : users) {
            jsonUsers.put(user);
        }
        jsonResponse.put(MessagesTopLevelConstants.VALUE.value(), jsonUsers);
        return jsonResponse;
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
        String code = _generateConversationCode(request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()));
        JSONObject response = new JSONObject();
        JSONObject responseValue = new JSONObject();
        responseValue.put(ChatMessagesConstants.CONVERSATION_CODE.value(), code);
        response.put(MessagesTopLevelConstants.VALUE.value(), responseValue);
        response.put(MessagesTopLevelConstants.TYPE.value(), RequestTypes.GET_CONVERSATION_CODE.value());
        return response;
    }

    private static JSONObject _procesJoinConversationUsingCode(JSONObject request) {
        int conversationId = _decodeConversationCode(
                request.getString(ChatMessagesConstants.CONVERSATION_CODE.value()));
        ArrayList<Integer> users = new ArrayList<>();
        users.add(request.getInt(ChatMessagesConstants.USER_ID.value()));
        System.out.println(users);
        _addUsersToConversation(users, conversationId);
        JSONObject responseValue = new JSONObject();
        var usersConversations = UserDataAccesor.getUserConversations(users.get(0));
        responseValue.put(MessagesTopLevelConstants.OUTCOME.value(), usersConversations.contains(conversationId));
        if (usersConversations.contains(conversationId)) {
            responseValue.put(ChatMessagesConstants.CONVERSATION_ID.value(), conversationId);
        }
        JSONObject response = new JSONObject();
        response.put(MessagesTopLevelConstants.VALUE.value(), responseValue);
        response.put(MessagesTopLevelConstants.TYPE.value(), RequestTypes.JOIN_CONVERSATION_WITH_CODE.value());
        return response;
    }

    private static int _decodeConversationCode(String code) {
        String chosenKeytable = CONVERSATION_CODE_KEYS[Character.getNumericValue(code.charAt(0))];
        String conversationCode = new String();

        for (int i = 1; i < code.length(); i++) {
            conversationCode = chosenKeytable.indexOf(code.charAt(i), 0) + conversationCode;
        }
        return Integer.parseInt(new StringBuilder(conversationCode).reverse().toString());

    }

    private static JSONObject _processChangeConversationName(JSONObject request) {
        String newName = request.getString(ChatMessagesConstants.NEW_CONVERSATION_NAME.value());
        var oldConversationData = ConversationDataAccesor
                .getData(request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()));
        oldConversationData.put(ChatMessagesConstants.CONVERSATION_NAME.value(), newName);
        ConversationDataSetter.setData(request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()),
                oldConversationData);
        JSONObject responseValue = new JSONObject();
        responseValue.put(MessagesTopLevelConstants.OUTCOME.value(), true);
        JSONObject response = new JSONObject();
        response.put(MessagesTopLevelConstants.VALUE.value(), responseValue);
        response.put(MessagesTopLevelConstants.TYPE.value(), RequestTypes.CHANGE_CONVERSATION_NAME.value());
        return response;
    }

    private static JSONObject _removeUserFromConversation(JSONObject request) {
        JSONObject responseValue = new JSONObject();
        JSONObject response = new JSONObject();
        System.out.println(UserDataAccesor.getUserConversations(request.getInt(ChatMessagesConstants.USER_ID.value())));
        if (!UserDataAccesor.getUserConversations(request.getInt(ChatMessagesConstants.USER_ID.value()))
                .contains(request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()))) {
            responseValue.put(MessagesTopLevelConstants.OUTCOME.value(), false);
            response.put(MessagesTopLevelConstants.VALUE.value(), responseValue);
            response.put(MessagesTopLevelConstants.TYPE.value(), RequestTypes.REMOVE_USER_FROM_CONVERSATION.value());
            return response;
        }
        ConversationDataSetter.RemoveUserConversationRelation(request.getInt(ChatMessagesConstants.USER_ID.value()),
                request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()));
        var conversationInfo = ConversationDataAccesor
                .getData(request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()));
        conversationInfo.put(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value(), Integer.toString(
                Integer.parseInt(conversationInfo.get(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value()))
                        - 1));
        ConversationDataSetter.setData(request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()), conversationInfo);
        responseValue.put(MessagesTopLevelConstants.OUTCOME.value(), true);
        response.put(MessagesTopLevelConstants.VALUE.value(), responseValue);
        response.put(MessagesTopLevelConstants.TYPE.value(), RequestTypes.REMOVE_USER_FROM_CONVERSATION.value());
        return response;
    }
}
