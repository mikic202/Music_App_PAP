package server.Login;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import server.DatabaseInteractors.UserDataAccesor;
import server.DatabaseInteractors.UserDataSetter;
import server.DatabaseInteractors.UserDatabaseInformation;

public class Login {
    public static JSONObject proces_requests(LoginRequestTypes req_type, JSONObject request) {
        return _generate_response(req_type, request);
    }

    private static JSONObject _login(JSONObject request) {
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
                true_result.put("user_id", user_info.get("ID"));
                result.put("value", true_result);
            }
            else
            {
                JSONObject false_result = new JSONObject();
                false_result.put("outcome", false);
                result.put("value", false_result);
            }
        }
        return result;
    }
    
    private static JSONObject _register(JSONObject request) {
        String email = request.getString("email");
        String nickname = request.getString("nickname");
        String password = request.getString("password");
        String confirm_password = request.getString("confirm_password");
        JSONObject result = new JSONObject();
        result.put("type", LoginRequestTypes.SEND_REGISTER.value());
        if(!password.equals(confirm_password)) {
        	result.put("outcome", false);
        	return result;
        }
        Hashtable<String, String> user_info = UserDataAccesor.get_data_with_email(email);
        if(!user_info.isEmpty()) {
        	result.put("outcome", false);
        	return result;
        }
        user_info = UserDataAccesor.get_data_with_name(nickname);
        if(!user_info.isEmpty()) {
        	result.put("outcome", false);
        	return result;
        }
        var data = new Hashtable<String, String>();
        data.put("username", nickname);
        data.put("email", email);
        data.put("password", password);
        UserDataSetter.add_data(data);
        result.put("outcome", true);
        return result;
    	
    }
    
    private static JSONObject _change_password(JSONObject request) {
        String email = request.getString("email");
        String old_password = request.getString("old_password");
        String confirm_old_password = request.getString("confirm_old_password");
        String new_password = request.getString("new_password");
        String confirm_new_password = request.getString("confirm_new_password");
        JSONObject result = new JSONObject();
    	if(!old_password.equals(confirm_old_password) || !new_password.equals(confirm_new_password)) {
        	result.put("outcome", false);
        	return result;
    	}
        Hashtable<String, String> user_info = UserDataAccesor.get_data_with_email(email);
        if(user_info.isEmpty()) {
        	result.put("outcome", false);
        	return result;
        }
        user_info.put("password", confirm_new_password);
        UserDataSetter.set_data(0, user_info);
        result.put("outcome", true);
        return result;
    }

    private static JSONObject _generate_response(LoginRequestTypes req_type, JSONObject request) {
        JSONObject response = new JSONObject();
        switch (req_type) {
            case SEND_LOGIN:
                response = _login(request);
                break;
            case SEND_REGISTER:
                response = _register(request);
                break;
            case SEND_CHANGE_PASSWORD:
                
                break;
        }
        return response;
    }
}
