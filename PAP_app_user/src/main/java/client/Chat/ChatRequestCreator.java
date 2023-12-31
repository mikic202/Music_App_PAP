package client.Chat;

import java.util.ArrayList;

import org.json.JSONObject;

import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.MessagesTopLevelConstants;

public class ChatRequestCreator {
        public static JSONObject createSendMsgRequest(int conversationId, int senderId, String text) {
                var request = new JSONObject();
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.SEND_MESSAGE.value());
                var value = new JSONObject();
                value.put(ChatMessagesConstants.MESSAGE_SENDER_ID.value(), senderId);
                value.put(ChatMessagesConstants.CONVERSATION_ID.value(), conversationId);
                value.put(ChatMessagesConstants.MESSAGE_TEXT.value(), text);
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                return request;
        }

        public static JSONObject createAddRonversationRequest(String name, ArrayList<Integer> users) {
                var request = new JSONObject();
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.CREATE_CONVERSATION.value());
                var value = new JSONObject();
                value.put(ChatMessagesConstants.CONVERSATION_NAME.value(), name);
                value.put(ChatMessagesConstants.USERS_IN_CONVERSATION.value(), users);
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                return request;
        }

        public static JSONObject createGetMessagesRequest(int conversationId) {
                var request = new JSONObject();
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.GET_MESSAGES.value());
                var value = new JSONObject();
                value.put(ChatMessagesConstants.CONVERSATION_ID.value(), conversationId);
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                return request;

        }

        public static JSONObject createGetConversationsRequest(int userId) {
                var request = new JSONObject();
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.GET_USERS_CONVERSATIONS.value());
                var value = new JSONObject();
                value.put(ChatMessagesConstants.USER_ID.value(), userId);
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                return request;
        }

        public static JSONObject createAddUserToConversationRequest(int conversationId,
                        ArrayList<Integer> users_ids) {
                var request = new JSONObject();
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.ADD_USER_TO_CONVERSATION.value());
                var value = new JSONObject();
                value.put(ChatMessagesConstants.CONVERSATION_ID.value(), conversationId);
                value.put(ChatMessagesConstants.USERS_IN_CONVERSATION.value(), users_ids);
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                return request;
        }

        public static JSONObject createGetUserInformationRequest(int userId) {
                var request = new JSONObject();
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.USER_INFO.value());
                var value = new JSONObject();
                value.put(ChatMessagesConstants.USER_ID.value(), userId);
                value.put(MessagesTopLevelConstants.TYPE.value(), ChatMessagesConstants.USER_ID.value());
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                return request;
        }

        public static JSONObject createGetUserInformationRequest(String username) {
                var request = new JSONObject();
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.USER_INFO.value());
                var value = new JSONObject();
                value.put(ChatMessagesConstants.USERNAME.value(), username);
                value.put(MessagesTopLevelConstants.TYPE.value(), ChatMessagesConstants.USERNAME.value());
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                return request;

        }

        public static JSONObject getUsersInConversation(int conversationId) {
                var request = new JSONObject();
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.GET_USERS_IN_CONVERSATION.value());
                var value = new JSONObject();
                value.put(ChatMessagesConstants.CONVERSATION_ID.value(), conversationId);
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                return request;
        }

        public static JSONObject createGetNewMessagesInConversation(int conversationId, int latestMessage) {
                var request = new JSONObject();
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.GET_LATEST_MESSAGE.value());
                var value = new JSONObject();
                value.put(ChatMessagesConstants.LATEST_MESSAGE.value(), latestMessage);
                value.put(ChatMessagesConstants.CONVERSATION_ID.value(), conversationId);
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                return request;
        }

        public static JSONObject createSendImageRequest(int conversationId, int senderId, byte[] image, String format) {
                JSONObject value = new JSONObject();
                value.put(ChatMessagesConstants.CONVERSATION_ID.value(), conversationId);
                value.put(ChatMessagesConstants.MESSAGE_SENDER_ID.value(), senderId);
                value.put(ChatMessagesConstants.IMAGE_MESSAGE.value(), image);
                value.put(ChatMessagesConstants.IMAGE_MESSAGE_FORMAT.value(), format);
                JSONObject request = new JSONObject();
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.SEND_IMAGE.value());
                return request;
        }

        public static JSONObject createGetConversationCodeRequest(int conversationId) {

                JSONObject value = new JSONObject();
                value.put(ChatMessagesConstants.CONVERSATION_ID.value(), conversationId);
                JSONObject request = new JSONObject();
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.GET_CONVERSATION_CODE.value());
                return request;
        }

        public static JSONObject createJoinConversationUsingCodeRequest(String code, int userId) {
                JSONObject value = new JSONObject();
                value.put(ChatMessagesConstants.CONVERSATION_CODE.value(), code);
                value.put(ChatMessagesConstants.USER_ID.value(), userId);
                JSONObject request = new JSONObject();
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                request.put(MessagesTopLevelConstants.TYPE.value(),
                                ChatRequestTypes.JOIN_CONVERSATION_WITH_CODE.value());
                return request;
        }

        public static JSONObject createChangeConversationNameRequest(int conversationId, String newName) {
                JSONObject value = new JSONObject();
                value.put(ChatMessagesConstants.CONVERSATION_ID.value(), conversationId);
                value.put(ChatMessagesConstants.NEW_CONVERSATION_NAME.value(), newName);
                JSONObject request = new JSONObject();
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                request.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.CHANGE_CONVERSATION_NAME.value());
                return request;
        }

        public static JSONObject createRemoveUserFromConversationRequest(int conversationId, int userId) {
                JSONObject value = new JSONObject();
                value.put(ChatMessagesConstants.CONVERSATION_ID.value(), conversationId);
                value.put(ChatMessagesConstants.USER_ID.value(), userId);
                JSONObject request = new JSONObject();
                request.put(MessagesTopLevelConstants.VALUE.value(), value);
                request.put(MessagesTopLevelConstants.TYPE.value(),
                                ChatRequestTypes.REMOVE_USER_FROM_CONVERSATION.value());
                return request;
        }
}
