package client.Chat;

import java.util.ArrayList;

import org.json.JSONObject;

public class RequestCreator {
        public static JSONObject createSendMsgRequest(int conversation_id, int sender_id, String text) {
                var request = new JSONObject();
                request.put("type", RequestTypes.SEND_MESSAGE.value());
                var value = new JSONObject();
                value.put("sender_id", sender_id);
                value.put("conversation_id", conversation_id);
                value.put("text", text);
                request.put("value", value);
                return request;
        }

        public static JSONObject createAddRonversationRequest(String name, ArrayList<Integer> users) {
                var request = new JSONObject();
                request.put("type", RequestTypes.CREATE_CONVERSATION.value());
                var value = new JSONObject();
                value.put("name", name);
                value.put("users", users);
                request.put("value", value);
                return request;
        }

        public static JSONObject createGetMessagesRequest(int conversation_id) {
                var request = new JSONObject();
                request.put("type", RequestTypes.GET_MESSAGES.value());
                var value = new JSONObject();
                value.put("conversation_id", conversation_id);
                request.put("value", value);
                return request;

        }

        public static JSONObject createGetConversationsRequest(int user_id) {
                var request = new JSONObject();
                request.put("type", RequestTypes.GET_USERS_CONVERSATIONS.value());
                var value = new JSONObject();
                value.put("user_id", user_id);
                request.put("value", value);
                return request;
        }

        public static JSONObject createAddUserToConversationRequest(int conversation_id,
                        ArrayList<Integer> users_ids) {
                var request = new JSONObject();
                request.put("type", RequestTypes.ADD_USER_TO_CONVERSATION.value());
                var value = new JSONObject();
                value.put("conversation_id", conversation_id);
                value.put("users", users_ids);
                request.put("value", value);
                return request;
        }

        public static JSONObject createGetUserInformationRequest(int user_id) {
                var request = new JSONObject();
                request.put("type", RequestTypes.USER_INFO.value());
                var value = new JSONObject();
                value.put("user_id", user_id);
                value.put("type", "user_id");
                request.put("value", value);
                return request;
        }

        public static JSONObject createGetUserInformationRequest(String username) {
                var request = new JSONObject();
                request.put("type", RequestTypes.USER_INFO.value());
                var value = new JSONObject();
                value.put("username", username);
                value.put("type", "username");
                request.put("value", value);
                return request;

        }

        public static JSONObject getUsersInConversation(int conversation_id) {
                var request = new JSONObject();
                request.put("type", RequestTypes.GET_USERS_IN_CONVERSATION.value());
                var value = new JSONObject();
                value.put("conversation_id", conversation_id);
                request.put("value", value);
                return request;
        }

        public static JSONObject createGetNewMessagesInConversation(int conversation_id, Integer latest_message) {
                var request = new JSONObject();
                request.put("type", RequestTypes.GET_LATEST_MESSAGE.value());
                var value = new JSONObject();
                value.put("latest_message", latest_message);
                value.put("conversation_id", conversation_id);
                request.put("value", value);
                return request;
        }
}
