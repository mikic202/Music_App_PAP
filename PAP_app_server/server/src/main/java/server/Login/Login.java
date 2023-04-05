package server.Login;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import server.DatabaseInteractors.UserDataAccesor;

public class Login {
    public JSONObject proces_requests(LoginRequestTypes req_type, JSONObject request) {
        return _generate_response(req_type, request);
    }

    private JSONObject _get_existance(JSONObject request) {
        String wanted_email = request.getString("email");
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<String> emails = new ArrayList<String>((UserDataAccesor.get_data_with_email(request.getString("email"))).keySet());
        String existance;
        for (String email : emails) {
            Hashtable<String, String> row = new Hashtable<String, String>();
            if (email.equals(wanted_email))
            {
                existance = "true";
            }
            else
            {
                existance = "false";
            }
            row.put(email, existance);
            response.add(row);
        }
        return _convert_response_to_json(response, LoginRequestTypes.GET_EXISTANCE);
    }

    private JSONObject _get_email(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<String> emails = new ArrayList<String>((UserDataAccesor.get_data_with_email(request.getString("email"))).keySet());
        for (String email : emails) {
            response.add(UserDataAccesor.get_data_with_email(email));
        }
        return _convert_response_to_json(response, LoginRequestTypes.GET_EMAIL);
    }

    private JSONObject _get_nickname(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<String> names = new ArrayList<String>((UserDataAccesor.get_data_with_name(request.getString("nickname"))).keySet());
        for (String name : names) {
            response.add(UserDataAccesor.get_data_with_name(name));
        }
        return _convert_response_to_json(response, LoginRequestTypes.GET_EMAIL);
    }

    

    private JSONObject _convert_response_to_json(ArrayList<Hashtable<String, String>> response, LoginRequestTypes req_type) {
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

    private JSONObject _generate_response(LoginRequestTypes req_type, JSONObject request) {
        JSONObject response = new JSONObject();
        switch (req_type) {
            case GET_EXISTANCE:
                response = _get_existance(request);
                break;
            case GET_EMAIL:
                response = _get_email(request);
                break;
            case GET_NICKNAME:
                response = _get_nickname(request);
                break;
        }
        return response;
    }
}
