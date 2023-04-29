package client.files;

import org.json.JSONObject;

public class UploadRequestCreator {
	public static JSONObject create_start_upload_request(int user_id, String file_name) {
		var value = new JSONObject();
		value.put("user_id", user_id);
		value.put("file_name", file_name);
		var data = new JSONObject();
		data.put("type", UploadRequestTypes.START_UPLOAD.value());
		data.put("value", value);
		return data;
	}
	
	public static JSONObject create_finish_upload_request(String uuid) {
		var value = new JSONObject();
		value.put("uuid", uuid);
		var data = new JSONObject();
		data.put("type", UploadRequestTypes.FINISH_UPLOAD.value());
		data.put("value", value);
		return data;
	}
}
