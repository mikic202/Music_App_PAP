package server.Login;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import server.DatabaseInteractors.UserDataAccesor;

public class Login {
    public static JSONObject proces_requests(LoginRequestTypes req_type, JSONObject request) {
        return _generate_response(req_type, request);
    }

    private static JSONObject _get_existance(JSONObject request) {
        String wanted_email = request.getString("email");
        JSONObject result = new JSONObject();
        result.put("type", LoginRequestTypes.SEND_LOGIN.value());
        String written_password = request.getString("password");
        Hashtable<String, String> user_info = UserDataAccesor.get_data_with_email(wanted_email);
        if (user_info.isEmpty())
        {
            JSONObject false_result = new JSONObject();
            false_result.put("outcome", false);
            result.put("value", false_result);
        }
        else
        {
            if (written_password.equals(user_info.get("password")))
            {
                JSONObject true_result = new JSONObject();
                true_result.put("outcome", true);
                true_result.put("username", user_info.get("username"));
                true_result.put("user_id", user_info.get("user_id"));
                result.put("value", true_result);
            }
            else
            {
                JSONObject true_result = new JSONObject();
                true_result.put("outcome", true);
                result.put("value", true_result);
            }
        }
        return result;
    } 

    private static JSONObject _convert_response_to_json(ArrayList<Hashtable<String, String>> response, LoginRequestTypes req_type) {
        JSONObject json_response = new JSONObject();
        json_response.put("type", req_type.value());
        JSONArray json_response_value = new JSONArray();
        for (Hashtable<String, String> element : response) {
            Set<String> keys = element.keySet();
            JSONObject json_element = new JSONObject();
            for (String key : keys) {
                json_element.put(key, element.get(key));
            }
            json_response_value.put(json_element);
        }
        json_response.put("value", json_response_value);
        return json_response;
    }

    private static JSONObject _generate_response(LoginRequestTypes req_type, JSONObject request) {
        JSONObject response = new JSONObject();
        switch (req_type) {
            case SEND_LOGIN:
                response = _get_existance(request);
                break;
            case SEND_REGISTER:
                
                break;
            case SEND_CHANGE_PASSWORD:
                
                break;
        }
        return response;
    }
}
