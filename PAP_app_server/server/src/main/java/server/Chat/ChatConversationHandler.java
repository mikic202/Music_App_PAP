package server.Chat;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONObject;

import server.DatabaseInteractors.ConversationDataAccesor;
import server.DatabaseInteractors.ConversationDataSetter;
import server.DatabaseInteractors.ConversationDatabsaeInformation;
import server.DatabaseInteractors.UserDataAccesor;
import server.DatabaseInteractors.UserDataSetter;
import server.ServerConnectionConstants.ChatMessagesConstants;
import server.ServerConnectionConstants.MessagesTopLevelConstants;

public class ChatConversationHandler {
    static final String[] CONVERSATION_CODE_KEYS = { "ocvdt&zvfl", "KzA#biPnxc", "*G$favyhmo", "!bs?j@iztq" };

    public static JSONObject createConversation(JSONObject request) {
        String name = request.getString(ChatMessagesConstants.CONVERSATION_NAME.value());
        int numberOfUsers = request.getJSONArray(ChatMessagesConstants.USERS_IN_CONVERSATION.value()).length();
        int newConversation = _addConversation(name, numberOfUsers);
        ArrayList<Integer> users = new ArrayList<>();
        for (int i = 0; i < request.getJSONArray(ChatMessagesConstants.USERS_IN_CONVERSATION.value()).length(); i++) {
            users.add(request.getJSONArray(ChatMessagesConstants.USERS_IN_CONVERSATION.value()).getInt(i));
        }
        _addUsersToConversation(users, newConversation);
        return JsonConverter.convertResponseToJson(ConversationDataAccesor.getData(newConversation),
                ChatRequestTypes.CREATE_CONVERSATION);
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

    public static JSONObject processAddUsersToConversation(JSONObject request) {
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
        return JsonConverter.convertResponseToJson(outcome, ChatRequestTypes.ADD_USER_TO_CONVERSATION);
    }

    public static JSONObject procesJoinConversationUsingCode(JSONObject request) {
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
        response.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.JOIN_CONVERSATION_WITH_CODE.value());
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
}
