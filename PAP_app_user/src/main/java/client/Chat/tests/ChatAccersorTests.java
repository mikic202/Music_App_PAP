package client.Chat.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import client.Chat.ChatAccesors;
import client.ServerConnector.ServerConnector;

public class ChatAccersorTests {

    private ServerConnector serverConnector;
    private ChatAccesors chatAccesor;

    // @Before
    // public void setUp() {
    // // mathApplication = new MathApplication();
    // // calcService = mock(CalculatorService.class);
    // // mathApplication.setCalculatorService(calcService);
    // }
    @Before
    public void setUp() {
        serverConnector = mock(ServerConnector.class);
        chatAccesor = new ChatAccesors(serverConnector);
    }

    @Test
    public void testUserInfoRequest() {
        //
        when
        // try (MockedStatic<ServerConnector> dummyAccesor =
        // Mockito.mockStatic(ServerConnector.class)) {
        // Hashtable<String, String> userData = new Hashtable<String, String>();

        //
        // dummyAccesor.when(() -> ServerConnector.send).thenReturn(userData);
        // var expectedResponse = _convertResponseToJson(userData,
        // RequestTypes.USER_INFO);
        // Response.getJSONObject("value").remove("password");
        // ssertEquals(
        // expectedResponse.toString(),
        // Chat.procesRequests(RequestTypes.USER_INFO, request).toString());
        // }
    }
}
