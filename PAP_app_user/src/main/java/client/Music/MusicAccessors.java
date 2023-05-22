package client.Music;

import org.json.JSONObject;

import client.ServerConnector.ServerConnector;

public class MusicAccessors {

    public MusicAccessors(ServerConnector serverConnectorInstance) {
        this.serverConnectorInstance = serverConnectorInstance;
    }

    public JSONObject sendStartStream(int userId, int chatId, int songId) {
        JSONObject processedRequest = MusicRequestCreator.createStartStreamRequest(userId, chatId, songId);
        JSONObject response = serverConnectorInstance.sendRequest(processedRequest);
        return response;
    }

    public JSONObject sendTerminateStream(int userId) {
        JSONObject processedRequest = MusicRequestCreator.createTerminateStreamRequest(userId);
        JSONObject response = serverConnectorInstance.sendRequest(processedRequest);
        return response;
    }

    public JSONObject sendCheckIfPlaying(int chatId) {
        JSONObject processedRequest = MusicRequestCreator.createCheckIfPlayingRequest(chatId);
        JSONObject response = serverConnectorInstance.sendRequest(processedRequest);
        return response;
    }

    public JSONObject sendJoinPlayingStream(int userId, int chatId) {
        JSONObject processedRequest = MusicRequestCreator.createJoinPlayingStreamRequest(userId, chatId);
        JSONObject response = serverConnectorInstance.sendRequest(processedRequest);
        return response;
    }

    public JSONObject sendPause(int userId) {
        JSONObject processedRequest = MusicRequestCreator.createPauseRequest(userId);
        JSONObject response = serverConnectorInstance.sendRequest(processedRequest);
        return response;
    }

    public JSONObject sendResume(int userId) {
        JSONObject processedRequest = MusicRequestCreator.createResumeRequest(userId);
        JSONObject response = serverConnectorInstance.sendRequest(processedRequest);
        return response;
    }

    private ServerConnector serverConnectorInstance;
}
