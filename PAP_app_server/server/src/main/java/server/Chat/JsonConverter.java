package server.Chat;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import server.ServerConnectionConstants.MessagesTopLevelConstants;

public class JsonConverter {

    public static JSONObject convertResponseToJson(ArrayList<Hashtable<String, String>> response,
            RequestTypes reqType) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(MessagesTopLevelConstants.TYPE.value(), reqType.value());
        JSONArray jsonResponseValue = new JSONArray();
        for (Hashtable<String, String> element : response) {
            Set<String> keys = element.keySet();
            JSONObject jsonElement = new JSONObject();
            for (String key : keys) {
                jsonElement.put(key, element.get(key));
            }
            jsonResponseValue.put(jsonElement);
        }
        jsonResponse.put(MessagesTopLevelConstants.VALUE.value(), jsonResponseValue);
        return jsonResponse;
    }

    public static JSONObject convertResponseToJson(Hashtable<String, String> response, RequestTypes reqType) {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(MessagesTopLevelConstants.TYPE.value(), reqType.value());
        JSONObject jsonResponseValue = new JSONObject();
        Set<String> keys = response.keySet();
        for (String key : keys) {
            jsonResponseValue.put(key, response.get(key));
        }
        jsonResponse.put(MessagesTopLevelConstants.VALUE.value(), jsonResponseValue);
        return jsonResponse;
    }

}
