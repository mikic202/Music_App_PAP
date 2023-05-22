package client.Chat;

import java.util.ArrayList;

import org.json.JSONObject;

public class RequestCreator {
        public static JSONObject createSendMsgRequest(int conversationId, int senderId, String text) {
                var request = new JSONObject();
                request.put("type", RequestTypes.SEND_MESSAGE.value());
                var value = new JSONObject();
                value.put("sender_id", senderId);
                value.put("conversation_id", conversationId);
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

        public static JSONObject createGetMessagesRequest(int conversationId) {
                var request = new JSONObject();
                request.put("type", RequestTypes.GET_MESSAGES.value());
                var value = new JSONObject();
                value.put("conversation_id", conversationId);
                request.put("value", value);
                return request;

        }

        public static JSONObject createGetConversationsRequest(int userId) {
                var request = new JSONObject();
                request.put("type", RequestTypes.GET_USERS_CONVERSATIONS.value());
                var value = new JSONObject();
                value.put("user_id", userId);
                request.put("value", value);
                return request;
        }

        public static JSONObject createAddUserToConversationRequest(int conversationId,
                        ArrayList<Integer> users_ids) {
                var request = new JSONObject();
                request.put("type", RequestTypes.ADD_USER_TO_CONVERSATION.value());
                var value = new JSONObject();
                value.put("conversation_id", conversationId);
                value.put("users", users_ids);
                request.put("value", value);
                return request;
        }

        public static JSONObject createGetUserInformationRequest(int userId) {
                var request = new JSONObject();
                request.put("type", RequestTypes.USER_INFO.value());
                var value = new JSONObject();
                value.put("user_id", userId);
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

        public static JSONObject getUsersInConversation(int conversationId) {
                var request = new JSONObject();
                request.put("type", RequestTypes.GET_USERS_IN_CONVERSATION.value());
                var value = new JSONObject();
                value.put("conversation_id", conversationId);
                request.put("value", value);
                return request;
        }

        public static JSONObject createGetNewMessagesInConversation(int conversationId, int latestMessage) {
                var request = new JSONObject();
                request.put("type", RequestTypes.GET_LATEST_MESSAGE.value());
                var value = new JSONObject();
                value.put("latest_message", latestMessage);
                value.put("conversation_id", conversationId);
                request.put("value", value);
                return request;
        }

        public static JSONObject createSendImageRequest(int conversationId, int senderId, byte[] image, String format) {
                JSONObject value = new JSONObject();
                value.put("conversation_id", conversationId);
                value.put("sender_id", senderId);
                value.put("image", image);
                value.put("format", format);
                JSONObject request = new JSONObject();
                request.put("value", value);
                request.put("type", RequestTypes.SEND_IMAGE.value());
                return request;
        }

        public static JSONObject createGetConversationCodeRequest(int conversationId) {

                JSONObject value = new JSONObject();
                value.put("conversation_id", conversationId);
                JSONObject request = new JSONObject();
                request.put("value", value);
                request.put("type", RequestTypes.GET_CONVERSATION_CODE.value());
                return request;
        }

        public static JSONObject createJoinConversationUsingCodeRequest(String code, int userId) {
                JSONObject value = new JSONObject();
                value.put("conversation_code", code);
                value.put("user_id", userId);
                JSONObject request = new JSONObject();
                request.put("value", value);
                request.put("type", RequestTypes.JOIN_CONVERSATION_WITH_CODE.value());
                return request;
        }
}
