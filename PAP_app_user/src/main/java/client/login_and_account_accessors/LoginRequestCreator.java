package client.login_and_account_accessors;

import org.json.JSONObject;

import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.LoggingMessagesConstants;
import client.ServerConnectionConstants.MessagesTopLevelConstants;

public class LoginRequestCreator {
	public static JSONObject createSendLoginRequest(String email, char[] password) {
		var value = new JSONObject();
		value.put(ChatMessagesConstants.EMAIL.value(), email);
		value.put(LoggingMessagesConstants.PASSWORD.value(), new String(password));
		var data = new JSONObject();
		data.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_LOGIN.value());
		data.put(MessagesTopLevelConstants.VALUE.value(), value);
		return data;
	}

	public static JSONObject createSendRegistrationRequest(String email, String nickname, char[] password,
			char[] confirm_password) {
		var value = new JSONObject();
		value.put(ChatMessagesConstants.EMAIL.value(), email);
		value.put("nickname", nickname);
		value.put(LoggingMessagesConstants.PASSWORD.value(), new String(password));
		value.put("confirm_password", new String(confirm_password));
		var data = new JSONObject();
		data.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_REGISTER.value());
		data.put(MessagesTopLevelConstants.VALUE.value(), value);
		return data;
	}

	public static JSONObject createChangePasswordRequest(String nickname, char[] old_password,
			char[] confirm_old_password, char[] new_password, char[] confirm_new_password) {
		var value = new JSONObject();
		value.put("nickname", nickname);
		value.put(LoggingMessagesConstants.OLD_PASSWORD.value(), new String(old_password));
		value.put("confirm_old_password", new String(confirm_old_password));
		value.put(LoggingMessagesConstants.NEW_PASSWORD.value(), new String(new_password));
		value.put("confirm_new_password", new String(confirm_new_password));
		var data = new JSONObject();
		data.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.SEND_CHANGE_PASSWORD.value());
		data.put(MessagesTopLevelConstants.VALUE.value(), value);
		return data;
	}

	public static JSONObject createRetrievePassword(String email) {
		var value = new JSONObject();
		value.put(ChatMessagesConstants.EMAIL.value(), email);
		var request = new JSONObject();
		request.put(MessagesTopLevelConstants.VALUE.value(), value);
		request.put(MessagesTopLevelConstants.TYPE.value(), LoginRequestTypes.RETRIEVE_PASSWORD.value());
		return request;
	}
}
