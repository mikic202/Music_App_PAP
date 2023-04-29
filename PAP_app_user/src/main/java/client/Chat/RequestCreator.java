package client.Chat;

import java.util.ArrayList;

import org.json.JSONObject;

public class RequestCreator {
        public static JSONObject createSendMsgRequest(int conversation_id, int sender_id, String text) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"sender_id\":%d, \"conversation_id\":%d, \"text\":%s}}",
                                                RequestTypes.SEND_MESSAGE.value(), sender_id, conversation_id, text));
        }

        public static JSONObject createAddRonversationRequest(String name, ArrayList<Integer> users) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"name\":%s, \"users\":%s}}",
                                                RequestTypes.CREATE_CONVERSATION.value(), name, users));
        }

        public static JSONObject createGetMessagesRequest(int conversation_id) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"conversation_id\":%s}}",
                                                RequestTypes.GET_MESSAGES.value(), conversation_id));

        }

        public static JSONObject createGetConversationsRequest(int user_id) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"user_id\":%s}}",
                                                RequestTypes.GET_USERS_CONVERSATIONS.value(), user_id));
        }

        public static JSONObject createAddUserToConversationRequest(int conversation_id,
                        ArrayList<Integer> users_ids) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"conversation_id\":%s, \"users\":%s}}",
                                                RequestTypes.ADD_USER_TO_CONVERSATION.value(), conversation_id,
                                                users_ids));
        }

        public static JSONObject createGetUserInformationRequest(int user_id) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"user_id\":%s, \"type\": user_id}}",
                                                RequestTypes.USER_INFO.value(), user_id));
        }

        public static JSONObject createGetUserInformationRequest(String username) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"username\":%s, \"type\": username}}",
                                                RequestTypes.USER_INFO.value(), username));
        }

        public static JSONObject getUsersInConversation(int conversation_id) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"conversation_id\":%s}}",
                                                RequestTypes.GET_USERS_IN_CONVERSATION.value(), conversation_id));
        }

        public static JSONObject createGetNewMessagesInConversation(int conversation, Integer latest_message) {
                return new JSONObject(
                                String.format("{\"type\":\"%s\", \"value\":{\"conversation_id\":%s, \"latest_message\":%s}}",
                                                RequestTypes.GET_LATEST_MESSAGE.value(), conversation, latest_message));
        }
}
