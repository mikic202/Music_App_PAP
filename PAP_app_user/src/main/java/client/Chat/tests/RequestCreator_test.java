package client.Chat.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import client.Chat.RequestCreator;
import client.Chat.RequestTypes;
import org.json.JSONObject;

public class RequestCreator_test {
    @Test
    public void test_creation_of_sendMessage_request() {
        JSONObject request = RequestCreator.createSendMsgRequest(1, 2, "some text");
        JSONObject expectedRequest = new JSONObject(String.format(
                "{\"type\":\"%s\", \"value\":{\"sender_id\":2, \"conversation_id\":1, \"text\":\"some text\"}}",
                RequestTypes.SEND_MESSAGE.value()));
        Assert.assertEquals(expectedRequest.toString(), request.toString());
    }

    @Test
    public void test_creation_of_addConversation_request() {
        ArrayList<Integer> users = new ArrayList<>();
        users.add(1);
        users.add(2);
        JSONObject request = RequestCreator.createAddRonversationRequest("some_name", users);
        JSONObject expectedRequest = new JSONObject(
                String.format("{\"type\":\"%s\", \"value\":{\"name\":\"some_name\", \"users\":%s}}",
                        RequestTypes.CREATE_CONVERSATION.value(), users));
        Assert.assertEquals(expectedRequest.toString(), request.toString());
    }

    @Test
    public void test_creation_of_get_messages_request() {
        JSONObject request = RequestCreator.createGetMessagesRequest(2);
        String expectedRequest = String.format("{\"type\":\"%s\",\"value\":{\"conversation_id\":2}}",
                RequestTypes.GET_MESSAGES.value());
        Assert.assertEquals(new JSONObject(expectedRequest).toString(), request.toString());
    }

    @Test
    public void test_creation_of_get_conversations_request() {
        JSONObject request = RequestCreator.createGetConversationsRequest(1);
        String expectedRequest = String.format("{\"type\":\"%s\",\"value\":{\"user_id\":1}}",
                RequestTypes.GET_USERS_CONVERSATIONS.value());
        Assert.assertEquals(new JSONObject(expectedRequest).toString(), request.toString());
    }

    @Test
    public void test_creation_of_add_user_to_conversation_request() {
        ArrayList<Integer> users = new ArrayList<>();
        users.add(1);
        users.add(2);
        JSONObject request = RequestCreator.createAddUserToConversationRequest(3, users);
        String expectedRequest = String.format("{\"type\":\"%s\",\"value\":{\"conversation_id\":3,\"users\":[1,2]}}",
                RequestTypes.ADD_USER_TO_CONVERSATION.value());
        Assert.assertEquals(new JSONObject(expectedRequest).toString(), request.toString());
    }

    @Test
    public void testCreationOfGetUserInformationWithIdRequest() {
        int userId = 1;
        JSONObject request = RequestCreator.createGetUserInformationRequest(userId);
        String expectedRequest = String.format("{\"type\":\"%s\",\"value\":{\"user_id\":1,\"type\":\"user_id\"}}",
                RequestTypes.USER_INFO.value());
        Assert.assertEquals(new JSONObject(expectedRequest).toString(), request.toString());
    }

    @Test
    public void testCreationOfGetUserInformatioWithUsernamenRequest() {
        String username = "user";
        JSONObject request = RequestCreator.createGetUserInformationRequest(username);
        String expectedRequest = String.format(
                "{\"type\":\"%s\",\"value\":{\"username\":\"user\",\"type\":\"username\"}}",
                RequestTypes.USER_INFO.value());
        Assert.assertEquals(new JSONObject(expectedRequest).toString(), request.toString());
    }

    @Test
    public void testCreationOfGetUsersInConversationRequest() {
        JSONObject request = RequestCreator.getUsersInConversation(2);
        String expectedRequest = String.format("{\"type\":\"%s\",\"value\":{\"conversation_id\":2}}",
                RequestTypes.GET_USERS_IN_CONVERSATION.value());
        Assert.assertEquals(new JSONObject(expectedRequest).toString(), request.toString());
    }

}
