package server.Chat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONObject;

import server.Main;
import server.DatabaseInteractors.ConversationDataAccesor;
import server.DatabaseInteractors.MessageDataAccesor;
import server.DatabaseInteractors.MessageDataSetter;
import server.DatabaseInteractors.MessagesDatabaseInformation;
import server.ServerConnectionConstants.ChatMessagesConstants;

public class ChatMessagesHandler {
    public static JSONObject getMessegesInConversation(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> messages = ConversationDataAccesor
                .getMessagesInConversation(request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()));
        for (Integer messageId : messages) {
            response.add(MessageDataAccesor.getData(messageId));
        }
        return JsonConverter.convertResponseToJson(response, ChatRequestTypes.GET_MESSAGES);
    }

    public static JSONObject sendMessage(JSONObject request) {
        int addedMsg = _putMessageInDatabase(request);
        Hashtable<String, String> data = MessageDataAccesor.getData(addedMsg);
        Main.updater.sendMessage(addedMsg, data.get(ChatMessagesConstants.CONVERSATION_ID.value()));
        return JsonConverter.convertResponseToJson(data, ChatRequestTypes.GET_MESSAGES);
    }

    public static JSONObject processSendImage(JSONObject request) {
        request.put(ChatMessagesConstants.MESSAGE_TEXT.value(),
                (request.getJSONArray(ChatMessagesConstants.IMAGE_MESSAGE.value())).toString());
        int newMessage = _putMessageInDatabase(request);
        MessageDataSetter.setIsImage(newMessage);

        Hashtable<String, String> data = MessageDataAccesor.getData(newMessage);
        Main.updater.sendMessage(newMessage, data.get(ChatMessagesConstants.CONVERSATION_ID.value()));

        return JsonConverter.convertResponseToJson(MessageDataAccesor.getData(newMessage), ChatRequestTypes.SEND_IMAGE);
    }

    public static JSONObject getNewMessagessInConversation(JSONObject request) {
        ArrayList<Hashtable<String, String>> messages = MessageDataAccesor
                .getMessagesOlderThanGiven(request.getInt(ChatMessagesConstants.LATEST_MESSAGE.value()),
                        request.getInt(ChatMessagesConstants.CONVERSATION_ID.value()));
        return JsonConverter.convertResponseToJson(messages, ChatRequestTypes.GET_LATEST_MESSAGE);
    }

    private static int _putMessageInDatabase(JSONObject request) {
        Hashtable<String, String> data = new Hashtable<>();
        data.put(MessagesDatabaseInformation.SENDER_COLUMN.value(),
                Integer.toString(request.getInt(ChatMessagesConstants.MESSAGE_SENDER_ID.value())));
        data.put(MessagesDatabaseInformation.CONVERSATION_COLUMN.value(),
                Integer.toString(request.getInt(ChatMessagesConstants.CONVERSATION_ID.value())));
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        data.put(MessagesDatabaseInformation.DATE_COLUMN.value(), timestamp.toString());
        data.put(MessagesDatabaseInformation.MESSAGE_COLUMN.value(),
                request.getString(ChatMessagesConstants.MESSAGE_TEXT.value()));
        return MessageDataSetter.addData(data);
    }
}
