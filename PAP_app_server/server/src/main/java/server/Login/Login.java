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
        String written_password = request.getString("password");
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<String> emails = new ArrayList<String>((UserDataAccesor.get_data_with_email(request.getString("email"))).keySet());
        ArrayList<String> passwords = new ArrayList<String>((UserDataAccesor.get_data_with_email(request.getString("password"))).keySet());
        for (String email : emails) {
            if (email.equals(wanted_email))
            {
                for (String password : passwords)
                {
                    if (password.equals(written_password))
                    {
                        Hashtable<String, String> outcome = new Hashtable<String, String>();
                        Hashtable<String, String> username = new Hashtable<String, String>();
                        Hashtable<String, String> user_id = new Hashtable<String, String>();
                        response.add(outcome);
                        response.add(username);
                        response.add(user_id);
                        break;
                    }
                    else
                    {
                        continue;
                    }
                }
            }
            else
            {
                continue;
            }
        }
        return _convert_response_to_json(response, LoginRequestTypes.SEND_LOGIN);
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
