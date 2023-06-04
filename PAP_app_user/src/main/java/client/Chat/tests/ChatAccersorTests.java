package client.Chat.tests;

import org.junit.Test;
import org.junit.Assert;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import client.ServerConnector.ServerConnector;

public class ChatAccersorTests {

    @Test
    public void testUserInfoRequest() {

        try (MockedStatic<ServerConnector> dummyAccesor = Mockito.mockStatic(ServerConnector.class)) {
            Hashtable<String, String> userData = new Hashtable<String, String>();

            dummyAccesor.when(() -> UserDataAccesor.getData(1)).thenReturn(userData);
            var expectedResponse = _convertResponseToJson(userData, RequestTypes.USER_INFO);
            expectedResponse.getJSONObject("value").remove("password");
            Assert.assertEquals(
                    expectedResponse.toString(),
                    Chat.procesRequests(RequestTypes.USER_INFO, request).toString());
        }
    }
}
