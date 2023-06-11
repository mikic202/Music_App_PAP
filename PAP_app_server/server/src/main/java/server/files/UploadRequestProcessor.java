package server.files;

import java.util.UUID;

import org.json.JSONObject;

import server.Main;
import server.ServerConnectionConstants.MessagesTopLevelConstants;

public class UploadRequestProcessor {
	public static JSONObject procesRequests(UploadRequestTypes req_type, JSONObject request) {
		return _generate_response(req_type, request);
	}

	private static JSONObject start(JSONObject request) {
		String user_id = String.valueOf(request.getInt("user_id"));
		String file_name = request.getString("file_name");
		boolean is_image = request.getBoolean("is_image");
		JSONObject result = new JSONObject();
		result.put(MessagesTopLevelConstants.TYPE.value(), UploadRequestTypes.START_UPLOAD.value());
		String uuid = UUID.randomUUID().toString();
		Main.uploader.startUpload(uuid, user_id, file_name, is_image);
		JSONObject value = new JSONObject();
		value.put("outcome", true);
		value.put("uuid", uuid);
		result.put(MessagesTopLevelConstants.VALUE.value(), value);

		return result;
	}

	private static JSONObject finish(JSONObject request) {
		String uuid = request.getString("uuid");
		JSONObject result = new JSONObject();
		result.put(MessagesTopLevelConstants.TYPE.value(), UploadRequestTypes.FINISH_UPLOAD.value());
		if (Main.uploader.finishUpload(uuid)) {
			JSONObject value = new JSONObject();
			value.put("outcome", true);
			result.put(MessagesTopLevelConstants.VALUE.value(), value);
			return result;
		}
		JSONObject value = new JSONObject();
		value.put("outcome", false);
		result.put(MessagesTopLevelConstants.VALUE.value(), value);

		return result;
	}

	private static JSONObject _generate_response(UploadRequestTypes req_type, JSONObject request) {
		JSONObject response = new JSONObject();
		switch (req_type) {
			case START_UPLOAD:
				response = start(request);
				break;
			case FINISH_UPLOAD:
				response = finish(request);
				break;
		}
		return response;
	}
}
