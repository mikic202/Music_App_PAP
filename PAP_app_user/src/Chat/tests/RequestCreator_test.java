package Chat.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import Chat.RequestCreator;
import Chat.RequestTypes;

public class RequestCreator_test {
    @Test
    public void test_creation_of_send_message_request() {
        String request = RequestCreator.create_send_msg_request(1, 2, "some text");
        String expected_request = String.format("%s;2;1;some text", RequestTypes.SEND_MESSAGE.value());
        Assert.assertEquals(expected_request, request);
    }

    @Test
    public void test_creation_of_add_conversation_request() {
        ArrayList<Integer> users = new ArrayList<>();
        users.add(1);
        users.add(2);
        String request = RequestCreator.create_add_conversation_request("some_name", users);
        String expected_request = String.format("%s;some_name;1;2", RequestTypes.CREATE_CONVERSATION.value());
        Assert.assertEquals(expected_request, request);
    }

    @Test
    public void test_creation_of_get_messages_request() {
        String request = RequestCreator.create_get_messages_request(2);
        String expected_request = String.format("%s;2",
                RequestTypes.GET_MESSAGES.value());
        Assert.assertEquals(expected_request, request);
    }

    @Test
    public void test_creation_of_get_conversations_request() {
        String request = RequestCreator.create_get_conversations_request(1);
        String expected_request = String.format("%s;1",
                RequestTypes.GET_USERS_CONVERSATIONS.value());
        Assert.assertEquals(expected_request, request);
    }

    @Test
    public void test_creation_of_add_user_to_conversation_request() {
        ArrayList<Integer> users = new ArrayList<>();
        users.add(1);
        users.add(2);
        String request = RequestCreator.create_add_user_to_conversation_request(3, users);
        String expected_request = String.format("%s;3;1;2", RequestTypes.ADD_USER_TO_CONVERSATION.value());
        Assert.assertEquals(expected_request, request);
    }

}
