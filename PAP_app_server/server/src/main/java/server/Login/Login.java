package server.Login;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import server.DatabaseInteractors.UserDataAccesor;
import server.DatabaseInteractors.UserDataSetter;
import server.DatabaseInteractors.UserDatabaseInformation;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Login {
    public static JSONObject procesRequests(LoginRequestTypes req_type, JSONObject request) {
        return _generateResponse(req_type, request);
    }

    private static JSONObject _login(JSONObject request) {
        String wanted_email = request.getString("email");
        JSONObject result = new JSONObject();
        result.put("type", LoginRequestTypes.SEND_LOGIN.value());
        String written_password = request.getString("password");
        Hashtable<String, String> user_info = UserDataAccesor.getDataWithEmail(wanted_email);
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
        Hashtable<String, String> user_info = UserDataAccesor.getDataWithEmail(email);
        if (!user_info.isEmpty()) {
            result.put("outcome", false);
            return result;
        }
        user_info = UserDataAccesor.getDataWithName(nickname);
        if (!user_info.isEmpty()) {
            result.put("outcome", false);
            return result;
        }
        var data = new Hashtable<String, String>();
        data.put("username", nickname);
        data.put("email", email);
        data.put("password", password);
        UserDataSetter.addData(data);
        result.put("outcome", true);
        return result;

    }

    private static JSONObject _changePassword(JSONObject request) {
        String old_password = request.getString("old_password");
        String new_password = request.getString("new_password");
        JSONObject result = new JSONObject();
        if (new_password.equals(old_password)) {
            result.put("outcome", false);
            return result;
        }
        Hashtable<String, String> user_info = UserDataAccesor.getData(request.getInt("user_id"));
        if (user_info.isEmpty()) {
            result.put("outcome", false);
            JSONObject response = new JSONObject();
            response.put("value", result);
            response.put("type", LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
            return response;
        }
        user_info.put("password", new_password);
        UserDataSetter.setData(request.getInt("user_id"), user_info);
        result.put("outcome", true);
        JSONObject response = new JSONObject();
        response.put("value", result);
        response.put("type", LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
        return response;
    }

    private static JSONObject _changeUsername(JSONObject request) {
        String username = request.getString("nickname");
        ;
        JSONObject result = new JSONObject();
        Hashtable<String, String> user_info = UserDataAccesor.getData(request.getInt("user_id"));
        if (user_info.isEmpty()) {
            result.put("outcome", false);
            JSONObject response = new JSONObject();
            response.put("value", result);
            response.put("type", LoginRequestTypes.SEND_CHANGE_NICKNAME.value());
            return response;
        }
        user_info.put("username", username);
        UserDataSetter.setData(request.getInt("user_id"), user_info);
        result.put("outcome", true);
        JSONObject response = new JSONObject();
        response.put("value", result);
        response.put("type", LoginRequestTypes.SEND_CHANGE_NICKNAME.value());
        return response;
    }

    private static JSONObject _changeEmail(JSONObject request) {
        String email = request.getString("email");
        JSONObject result = new JSONObject();
        Hashtable<String, String> user_info = UserDataAccesor.getData(request.getInt("user_id"));
        if (user_info.isEmpty()) {
            result.put("outcome", false);
            JSONObject response = new JSONObject();
            response.put("value", result);
            response.put("type", LoginRequestTypes.SEND_CHANGE_EMAIL.value());
            return response;
        }
        user_info.put("email", email);
        UserDataSetter.setData(request.getInt("user_id"), user_info);
        result.put("outcome", true);
        JSONObject response = new JSONObject();
        response.put("value", result);
        response.put("type", LoginRequestTypes.SEND_CHANGE_EMAIL.value());
        return response;
    }

    private static JSONObject _retrievePassword(JSONObject request) {
        JSONObject result = new JSONObject();
        Hashtable<String, String> user_info = UserDataAccesor.getData(request.getInt("user_id"));
        if (user_info.isEmpty()) {
            result.put("outcome", false);
            JSONObject response = new JSONObject();
            response.put("value", result);
            response.put("type", LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
            return response;
        }
        String email = request.getString("email");
        String message = "Please DO NOT responde to this email\nNew Password: ";
        String newPassword = _generateRandomString();
        message += newPassword;
        message += "\n Please reset the Password after logging in";
        user_info.put("password", newPassword);
        UserDataSetter.setData(request.getInt("user_id"), user_info);
        _sendMessage(email, message);
        result.put("outcome", true);
        JSONObject response = new JSONObject();
        response.put("value", result);
        response.put("type", LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
        return response;
    }

    private static void _sendMessage(String email, String messageToSend) {
        // TODO add sending email
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "localhost");
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("retrieve_password@gmail.com"));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            message.setSubject("New Password");

            // Now set the actual message
            message.setText(messageToSend);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    private static String _generateRandomString() {
        int charAmount = (int) (Math.random() * (20 - 10 + 1) + 10);
        String alphabet = "abcdefghijklmnoprstuwxyz";
        String randomString = "";
        for (int i = 0; i < charAmount; i++) {
            int nextChar = (int) (Math.random() * (alphabet.length()));
            char newChar = alphabet.charAt(nextChar);
            if (Math.random() > 0.5) {
                newChar = Character.toUpperCase(newChar);
            }
            randomString += newChar;
        }
        return randomString;
    }

    private static JSONObject _generateResponse(LoginRequestTypes req_type, JSONObject request) {
        JSONObject response = new JSONObject();
        switch (req_type) {
            case SEND_LOGIN:
                response = _login(request);
                break;
            case SEND_REGISTER:
                response = _register(request);
                break;
            case SEND_CHANGE_PASSWORD:
                response = _changePassword(request);
                break;
            case SEND_CHANGE_EMAIL:
                response = _changeEmail(request);
                break;
            case SEND_CHANGE_NICKNAME:
                response = _changeUsername(request);
                break;
            case RETRIEVE_PASSWORD:
                response = _retrievePassword(request);
                break;
        }
        return response;
    }
}
