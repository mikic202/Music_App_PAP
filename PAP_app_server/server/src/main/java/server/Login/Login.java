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
        if (user_info.isEmpty()) {
            JSONObject false_result = new JSONObject();
            false_result.put("outcome", false);
            result.put("value", false_result);
        } else {
            if (written_password.equals(user_info.get("password"))) {
                JSONObject true_result = new JSONObject();
                true_result.put("outcome", true);
                true_result.put("username", user_info.get("username"));
                true_result.put("user_id", user_info.get("ID"));
                result.put("value", true_result);
            } else {
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
        if (!password.equals(confirm_password)) {
            result.put("outcome", false);
            return result;
        }
        Hashtable<String, String> user_info = UserDataAccesor.get_data_with_email(email);
        if (!user_info.isEmpty()) {
            result.put("outcome", false);
            return result;
        }
        user_info = UserDataAccesor.get_data_with_name(nickname);
        if (!user_info.isEmpty()) {
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
        String old_password = request.getString("old_password");
        String new_password = request.getString("new_password");
        JSONObject result = new JSONObject();
        if (new_password.equals(old_password)) {
            result.put("outcome", false);
            return result;
        }
        Hashtable<String, String> user_info = UserDataAccesor.get_data(request.getInt("user_id"));
        if (user_info.isEmpty()) {
            result.put("outcome", false);
            JSONObject response = new JSONObject();
            response.put("value", result);
            response.put("type", LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
            return response;
        }
        user_info.put("password", new_password);
        UserDataSetter.set_data(request.getInt("user_id"), user_info);
        result.put("outcome", true);
        JSONObject response = new JSONObject();
        response.put("value", result);
        response.put("type", LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
        return response;
    }

    private static JSONObject _change_username(JSONObject request) {
        String username = request.getString("nickname");
        ;
        JSONObject result = new JSONObject();
        Hashtable<String, String> user_info = UserDataAccesor.get_data(request.getInt("user_id"));
        if (user_info.isEmpty()) {
            result.put("outcome", false);
            JSONObject response = new JSONObject();
            response.put("value", result);
            response.put("type", LoginRequestTypes.SEND_CHANGE_NICKNAME.value());
            return response;
        }
        user_info.put("username", username);
        UserDataSetter.set_data(request.getInt("user_id"), user_info);
        result.put("outcome", true);
        JSONObject response = new JSONObject();
        response.put("value", result);
        response.put("type", LoginRequestTypes.SEND_CHANGE_NICKNAME.value());
        return response;
    }

    private static JSONObject _change_email(JSONObject request) {
        String email = request.getString("email");
        ;
        JSONObject result = new JSONObject();
        Hashtable<String, String> user_info = UserDataAccesor.get_data(request.getInt("user_id"));
        if (user_info.isEmpty()) {
            result.put("outcome", false);
            JSONObject response = new JSONObject();
            response.put("value", result);
            response.put("type", LoginRequestTypes.SEND_CHANGE_EMAIL.value());
            return response;
        }
        user_info.put("email", email);
        UserDataSetter.set_data(request.getInt("user_id"), user_info);
        result.put("outcome", true);
        JSONObject response = new JSONObject();
        response.put("value", result);
        response.put("type", LoginRequestTypes.SEND_CHANGE_EMAIL.value());
        return response;
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
                response = _change_password(request);
                break;
            case SEND_CHANGE_EMAIL:
                response = _change_email(request);
                break;
            case SEND_CHANGE_NICKNAME:
                response = _change_username(request);
                break;
        }
        return response;
    }
}
