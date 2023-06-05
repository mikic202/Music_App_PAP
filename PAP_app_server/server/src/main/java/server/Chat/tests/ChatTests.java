package server.Chat.tests;

import java.util.Hashtable;
import java.util.Set;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import server.Chat.Chat;
import server.Chat.RequestTypes;
import server.DatabaseInteractors.UserDataAccesor;

public class ChatTests {
    private static JSONObject _convertResponseToJson(Hashtable<String, String> response, RequestTypes reqType) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("type", reqType.value());
        JSONObject jsonResponseValue = new JSONObject();
        Set<String> keys = response.keySet();
        for (String key : keys) {
            jsonResponseValue.put(key, response.get(key));
        }
        jsonResponse.put("value", jsonResponseValue);
        return jsonResponse;
    }

    @Test
    public void testUserInfoRequest() {
        var request = new JSONObject();
        request.put("user_id", 1);
        request.put("type", "user_id");
        try (MockedStatic<UserDataAccesor> dummyAccesor = Mockito.mockStatic(UserDataAccesor.class)) {
            Hashtable<String, String> userData = new Hashtable<String, String>();
            userData.put("ID", "1");
            userData.put("username", "some_user");
            userData.put("email", "some@email");
            userData.put("password", "12345");
            userData.put("profile_picture", "0");
            dummyAccesor.when(() -> UserDataAccesor.getData(1)).thenReturn(userData);
            var expectedResponse = _convertResponseToJson(userData, RequestTypes.USER_INFO);
            expectedResponse.getJSONObject("value").remove("password");
            Assert.assertEquals(
                    expectedResponse.toString(),
                    Chat.procesRequests(RequestTypes.USER_INFO, request).toString());
        }
    }

    @Test
    public void testUserInfoRequestWithUsername() {
        var request = new JSONObject();
        request.put("username", "some_user");
        request.put("type", "username");
        try (MockedStatic<UserDataAccesor> dummyAccesor = Mockito.mockStatic(UserDataAccesor.class)) {
            Hashtable<String, String> userData = new Hashtable<String, String>();
            userData.put("ID", "1");
            userData.put("username", "some_user");
            userData.put("email", "some@email");
            userData.put("password", "12345");
            userData.put("profile_picture", "0");
            dummyAccesor.when(() -> UserDataAccesor.getData("username", "some_user")).thenReturn(userData);
            var expectedResponse = _convertResponseToJson(userData, RequestTypes.USER_INFO);
            expectedResponse.getJSONObject("value").remove("password");
            Assert.assertEquals(
                    expectedResponse.toString(),
                    Chat.procesRequests(RequestTypes.USER_INFO, request).toString());
        }
    }
}
