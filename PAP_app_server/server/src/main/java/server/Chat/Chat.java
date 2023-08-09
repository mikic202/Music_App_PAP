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

    public static JSONObject procesRequests(ChatRequestTypes reqType, JSONObject request) {
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

    private static JSONObject _generateResponse(ChatRequestTypes reqType, JSONObject request) {
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
                response = ChatConversationHandler.createConversation(request);
                break;
            case ADD_USER_TO_CONVERSATION:
                response = ChatConversationHandler.processAddUsersToConversation(request);
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
                response = ChatConversationHandler.procesJoinConversationUsingCode(request);
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
        return JsonConverter.convertResponseToJson(response, ChatRequestTypes.GET_USERS_CONVERSATIONS);
    }

    private static JSONObject _checkUsersInfo(JSONObject request) {
        if (request.getString(MessagesTopLevelConstants.TYPE.value()).equals(ChatMessagesConstants.USERNAME.value())) {
            Hashtable<String, String> hashResponse = UserDataAccesor
                    .getData(UserDatabaseInformation.USERNAME_COLUMN.value(),
                            request.getString(ChatMessagesConstants.USERNAME.value()));
            hashResponse.remove(LoggingMessagesConstants.PASSWORD.value());
            return JsonConverter.convertResponseToJson(hashResponse, ChatRequestTypes.USER_INFO);
        }
        Hashtable<String, String> hashResponse = UserDataAccesor
                .getData(request.getInt(ChatMessagesConstants.USER_ID.value()));
        hashResponse.remove(LoggingMessagesConstants.PASSWORD.value());
        return JsonConverter.convertResponseToJson(hashResponse, ChatRequestTypes.USER_INFO);
    }

    private static JSONObject _getUsersInConversation(JSONObject request) {
        ArrayList<Integer> users = ConversationDataAccesor
                .getUsersInConversation(request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()));
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.GET_USERS_CONVERSATIONS.value());
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
        response.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.GET_CONVERSATION_CODE.value());
        return response;
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
        response.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.CHANGE_CONVERSATION_NAME.value());
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
            response.put(MessagesTopLevelConstants.TYPE.value(),
                    ChatRequestTypes.REMOVE_USER_FROM_CONVERSATION.value());
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
        response.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.REMOVE_USER_FROM_CONVERSATION.value());
        return response;
    }
}
