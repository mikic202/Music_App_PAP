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
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import server.ServerConnectionConstants.MessagesTopLevelConstants;
import server.ServerConnectionConstants.ChatMessagesConstants;
import server.ServerConnectionConstants.LoggingMessagesConstants;

public class Login {
    public static JSONObject procesRequests(LoginRequestTypes req_type, JSONObject request) {
        return _generateResponse(req_type, request);
    }

    private static JSONObject _login(JSONObject request) {
        String wanted_email = request.getString(ChatMessagesConstants.EMAIL.value());
        JSONObject result = new JSONObject();
        result.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_LOGIN.value());
        String written_password = request.getString(LoggingMessagesConstants.PASSWORD.value());
        Hashtable<String, String> user_info = UserDataAccesor.getDataWithEmail(wanted_email);
        if (user_info.isEmpty()) {
            JSONObject false_result = new JSONObject();
            false_result.put(MessagesTopLevelConstants.OUTCOME.value(), false);
            result.put(MessagesTopLevelConstants.VALUE.value(), false_result);
        } else {
            if (written_password.equals(user_info.get(LoggingMessagesConstants.PASSWORD.value()))) {
                JSONObject true_result = new JSONObject();
                true_result.put(MessagesTopLevelConstants.OUTCOME.value(), true);
                true_result.put(ChatMessagesConstants.USERNAME.value(),
                        user_info.get(ChatMessagesConstants.USERNAME.value()));
                true_result.put(ChatMessagesConstants.USER_ID.value(), user_info.get("ID"));
                true_result.put("profile_picture", user_info.get("profile_picture"));
                result.put(MessagesTopLevelConstants.VALUE.value(), true_result);
            } else {
                JSONObject false_result = new JSONObject();
                false_result.put(MessagesTopLevelConstants.OUTCOME.value(), false);
                result.put(MessagesTopLevelConstants.VALUE.value(), false_result);
            }
        }
        return result;
    }

    private static JSONObject _register(JSONObject request) {
        String email = request.getString(ChatMessagesConstants.EMAIL.value());
        String nickname = request.getString("nickname");
        String password = request.getString(LoggingMessagesConstants.PASSWORD.value());
        String confirm_password = request.getString("confirm_password");
        JSONObject result = new JSONObject();
        result.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_REGISTER.value());
        if (!password.equals(confirm_password)) {
            result.put(MessagesTopLevelConstants.OUTCOME.value(), false);
            return result;
        }
        Hashtable<String, String> user_info = UserDataAccesor.getDataWithEmail(email);
        if (!user_info.isEmpty()) {
            result.put(MessagesTopLevelConstants.OUTCOME.value(), false);
            return result;
        }
        user_info = UserDataAccesor.getDataWithName(nickname);
        if (!user_info.isEmpty()) {
            result.put(MessagesTopLevelConstants.OUTCOME.value(), false);
            return result;
        }
        var data = new Hashtable<String, String>();
        data.put(ChatMessagesConstants.USERNAME.value(), nickname);
        data.put(ChatMessagesConstants.EMAIL.value(), email);
        data.put(LoggingMessagesConstants.PASSWORD.value(), password);
        UserDataSetter.addData(data);
        result.put(MessagesTopLevelConstants.OUTCOME.value(), true);
        return result;

    }

    private static JSONObject _changePassword(JSONObject request) {
        String old_password = request.getString(LoggingMessagesConstants.OLD_PASSWORD.value());
        String new_password = request.getString(LoggingMessagesConstants.NEW_PASSWORD.value());
        JSONObject result = new JSONObject();
        if (new_password.equals(old_password)) {
            result.put(MessagesTopLevelConstants.OUTCOME.value(), false);
            return result;
        }
        Hashtable<String, String> user_info = UserDataAccesor
                .getData(request.getInt(ChatMessagesConstants.USER_ID.value()));
        if (user_info.isEmpty() || !user_info.get(LoggingMessagesConstants.PASSWORD.value()).equals(old_password)) {
            result.put(MessagesTopLevelConstants.OUTCOME.value(), false);
            JSONObject response = new JSONObject();
            response.put(MessagesTopLevelConstants.VALUE.value(), result);
            response.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
            return response;
        }
        user_info.put(LoggingMessagesConstants.PASSWORD.value(), new_password);
        UserDataSetter.setData(request.getInt(ChatMessagesConstants.USER_ID.value()), user_info);
        result.put(MessagesTopLevelConstants.OUTCOME.value(), true);
        JSONObject response = new JSONObject();
        response.put(MessagesTopLevelConstants.VALUE.value(), result);
        response.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
        return response;
    }

    private static JSONObject _changeUsername(JSONObject request) {
        String username = request.getString("nickname");
        ;
        JSONObject result = new JSONObject();
        Hashtable<String, String> user_info = UserDataAccesor
                .getData(request.getInt(ChatMessagesConstants.USER_ID.value()));
        if (user_info.isEmpty()) {
            result.put(MessagesTopLevelConstants.OUTCOME.value(), false);
            JSONObject response = new JSONObject();
            response.put(MessagesTopLevelConstants.VALUE.value(), result);
            response.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_CHANGE_NICKNAME.value());
            return response;
        }
        user_info.put(ChatMessagesConstants.USERNAME.value(), username);
        UserDataSetter.setData(request.getInt(ChatMessagesConstants.USER_ID.value()), user_info);
        result.put(MessagesTopLevelConstants.OUTCOME.value(), true);
        JSONObject response = new JSONObject();
        response.put(MessagesTopLevelConstants.VALUE.value(), result);
        response.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_CHANGE_NICKNAME.value());
        return response;
    }

    private static JSONObject _changeEmail(JSONObject request) {
        String email = request.getString(ChatMessagesConstants.EMAIL.value());
        JSONObject result = new JSONObject();
        Hashtable<String, String> user_info = UserDataAccesor
                .getData(request.getInt(ChatMessagesConstants.USER_ID.value()));
        if (user_info.isEmpty()) {
            result.put(MessagesTopLevelConstants.OUTCOME.value(), false);
            JSONObject response = new JSONObject();
            response.put(MessagesTopLevelConstants.VALUE.value(), result);
            response.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_CHANGE_EMAIL.value());
            return response;
        }
        user_info.put(ChatMessagesConstants.EMAIL.value(), email);
        UserDataSetter.setData(request.getInt(ChatMessagesConstants.USER_ID.value()), user_info);
        result.put(MessagesTopLevelConstants.OUTCOME.value(), true);
        JSONObject response = new JSONObject();
        response.put(MessagesTopLevelConstants.VALUE.value(), result);
        response.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_CHANGE_EMAIL.value());
        return response;
    }

    private static JSONObject _retrievePassword(JSONObject request) {
        JSONObject result = new JSONObject();
        Hashtable<String, String> user_info = UserDataAccesor
                .getDataWithEmail(request.getString(ChatMessagesConstants.EMAIL.value()));
        if (user_info.isEmpty()) {
            result.put(MessagesTopLevelConstants.OUTCOME.value(), false);
            JSONObject response = new JSONObject();
            response.put(MessagesTopLevelConstants.VALUE.value(), result);
            response.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
            return response;
        }
        final String email = request.getString(ChatMessagesConstants.EMAIL.value());
        String newPassword = _generateRandomString();
        final String message = "Please DO NOT responde to this email\nNew Password: " + newPassword
                + "\n Please reset the Password after logging in";
        user_info.put(LoggingMessagesConstants.PASSWORD.value(), newPassword);
        System.out.println(user_info);
        UserDataSetter.setData(Integer.parseInt(user_info.get("ID")), user_info);
        new Thread(new Runnable() {
            public void run() {
                _sendMessage(email, message);
            }
        }).start();
        result.put(MessagesTopLevelConstants.OUTCOME.value(), true);
        JSONObject response = new JSONObject();
        response.put(MessagesTopLevelConstants.VALUE.value(), result);
        response.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
        return response;
    }

    public static void _sendMessage(String email, String messageToSend) {
        String from = "123.pap.app.verif.123@gmail.com";

        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "cxlawlqoefcyfuhr");
            }
        });
        session.setDebug(true);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Password retrieving");
            message.setText(messageToSend);
            System.out.println("sending...");
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
