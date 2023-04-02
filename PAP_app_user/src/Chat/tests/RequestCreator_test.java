package Chat.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import Chat.RequestCreator;
import Chat.RequestTypes;
import org.json.JSONObject;

public class RequestCreator_test {
    @Test
    public void test_creation_of_send_message_request() {
        JSONObject request = RequestCreator.create_send_msg_request(1, 2, "some text");
        JSONObject expected_request = new JSONObject(String.format(
                "{\"type\":\"%s\", \"value\":{\"sender_id\":2, \"conversation_id\":1, \"text\":\"some text\"}}",
                RequestTypes.SEND_MESSAGE.value()));
        Assert.assertEquals(expected_request.toString(), request.toString());
    }

    @Test
    public void test_creation_of_add_conversation_request() {
        ArrayList<Integer> users = new ArrayList<>();
        users.add(1);
        users.add(2);
        JSONObject request = RequestCreator.create_add_conversation_request("some_name", users);
        JSONObject expected_request = new JSONObject(
                String.format("{\"type\":\"%s\", \"value\":{\"name\":\"some_name\", \"users\":%s}}",
                        RequestTypes.CREATE_CONVERSATION.value(), users));
        Assert.assertEquals(expected_request.toString(), request.toString());
    }

    @Test
    public void test_creation_of_get_messages_request() {
        JSONObject request = RequestCreator.create_get_messages_request(2);
        String expected_request = String.format("{\"type\":\"%s\",\"value\":{\"conversation_id\":2}}",
                RequestTypes.GET_MESSAGES.value());
        Assert.assertEquals(expected_request.toString(), request.toString());
    }

    @Test
    public void test_creation_of_get_conversations_request() {
        JSONObject request = RequestCreator.create_get_conversations_request(1);
        String expected_request = String.format("{\"type\":\"%s\",\"value\":{\"user_id\":1}}",
                RequestTypes.GET_USERS_CONVERSATIONS.value());
        Assert.assertEquals(expected_request.toString(), request.toString());
    }

    @Test
    public void test_creation_of_add_user_to_conversation_request() {
        ArrayList<Integer> users = new ArrayList<>();
        users.add(1);
        users.add(2);
        JSONObject request = RequestCreator.create_add_user_to_conversation_request(3, users);
        String expected_request = String.format("{\"type\":\"%s\",\"value\":{\"conversation_id\":3,\"users\":[1,2]}}",
                RequestTypes.ADD_USER_TO_CONVERSATION.value());
        Assert.assertEquals(expected_request.toString(), request.toString());
    }

}
