package client.files;

import org.json.JSONObject;

import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.MessagesTopLevelConstants;

public class UploadRequestCreator {
	public static JSONObject create_start_upload_request(int userId, String file_name, boolean isImage) {
		var value = new JSONObject();
		value.put(ChatMessagesConstants.USER_ID.value(), userId);
		value.put("file_name", file_name);
		value.put(ChatMessagesConstants.IS_IMAGE.value(), isImage);
		var data = new JSONObject();
		data.put(MessagesTopLevelConstants.TYPE.value(), UploadRequestTypes.START_UPLOAD.value());
		data.put(MessagesTopLevelConstants.VALUE.value(), value);
		return data;
	}

	public static JSONObject create_finish_upload_request(String uuid) {
		var value = new JSONObject();
		value.put("uuid", uuid);
		var data = new JSONObject();
		data.put(MessagesTopLevelConstants.TYPE.value(), UploadRequestTypes.FINISH_UPLOAD.value());
		data.put(MessagesTopLevelConstants.VALUE.value(), value);
		return data;
	}
}
