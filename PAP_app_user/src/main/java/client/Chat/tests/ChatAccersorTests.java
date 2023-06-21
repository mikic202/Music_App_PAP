package client.Chat.tests;

import client.ServerConnector.ServerConnector;
import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.Chat.ChatRequestTypes;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import client.Chat.ChatAccesors;

public class ChatAccersorTests {

    private ServerConnector serverConnector;
    private ChatAccesors chatAccesor;

    // @Before
    // public void setUp() {
    // // mathApplication = new MathApplication();
    // // calcService = mock(CalculatorService.class);
    // // mathApplication.setCalculatorService(calcService);
    // }
    // @Before
    // public void setUp() {
    // serverConnector = Mockito.(ServerConnector.class);
    // chatAccesor = new ChatAccesors(serverConnector);
    // }

    @Test
    public void testUserInfoRequest() {
        //
        serverConnector = Mockito.mock(ServerConnector.class);
        chatAccesor = new ChatAccesors(serverConnector);

        var response = new JSONObject();
        response.put(MessagesTopLevelConstants.TYPE.value(), ChatRequestTypes.USER_INFO.value());
        var responseValue = new JSONObject();
        responseValue.put(ChatMessagesConstants.USER_ID.value(), 1);
        responseValue.put(ChatMessagesConstants.EMAIL.value(), "some@email");
        response.put(MessagesTopLevelConstants.VALUE.value(), responseValue);

        when(serverConnector.sendRequest(any(JSONObject.class))).thenReturn(response);
        Assert.assertEquals(response.toString(), chatAccesor.getUserInfo(1).toString());
    }
}
