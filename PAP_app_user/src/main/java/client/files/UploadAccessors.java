package client.files;

import org.json.JSONObject;

import client.ServerConnector.ServerConnector;

public class UploadAccessors {
    private ServerConnector serverConnector;

    public UploadAccessors(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    public JSONObject startUpload(int userId, String file_name, boolean isImage) {
        JSONObject procesed_request = UploadRequestCreator.create_start_upload_request(userId, file_name, isImage);
        JSONObject response = serverConnector.sendRequest(procesed_request);
        return response;
    }

    public JSONObject finishUpload(String uuid) {
        JSONObject procesed_request = UploadRequestCreator.create_finish_upload_request(uuid);
        JSONObject response = serverConnector.sendRequest(procesed_request);
        return response;
    }
}
