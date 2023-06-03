package client.Music;

import org.json.JSONObject;

import client.ServerConnectionConstants.ChatMessagesConstants;
import client.ServerConnectionConstants.MessagesTopLevelConstants;

public class MusicRequestCreator {

    public static JSONObject createStartStreamRequest(int userId, int chatId, int songId) {
        var value = new JSONObject();
        value.put(ChatMessagesConstants.USER_ID.value(), userId);
        value.put("chat_id", chatId);
        value.put("song_id", songId);
        var data = new JSONObject();
        data.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.START_STREAM.value());
        data.put(MessagesTopLevelConstants.VALUE.value(), value);
        return data;
    }

    public static JSONObject createTerminateStreamRequest(int userId) {
        var value = new JSONObject();
        value.put(ChatMessagesConstants.USER_ID.value(), userId);
        var data = new JSONObject();
        data.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.TERMINATE_STREAM.value());
        data.put(MessagesTopLevelConstants.VALUE.value(), value);
        return data;
    }

    public static JSONObject createCheckIfPlayingRequest(int chatId) {
        var value = new JSONObject();
        value.put("chat_id", chatId);
        var data = new JSONObject();
        data.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.CHECK_IF_PLAYING.value());
        data.put(MessagesTopLevelConstants.VALUE.value(), value);
        return data;
    }

    public static JSONObject createJoinPlayingStreamRequest(int userId, int chatId) {
        var value = new JSONObject();
        value.put(ChatMessagesConstants.USER_ID.value(), userId);
        value.put("chat_id", chatId);
        var data = new JSONObject();
        data.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.JOIN_PLAYING_STREAM.value());
        data.put(MessagesTopLevelConstants.VALUE.value(), value);
        return data;
    }

    public static JSONObject createPauseRequest(int userId) {
        var value = new JSONObject();
        value.put(ChatMessagesConstants.USER_ID.value(), userId);
        var data = new JSONObject();
        data.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.PAUSE_STREAM.value());
        data.put(MessagesTopLevelConstants.VALUE.value(), value);
        return data;
    }

    public static JSONObject createResumeRequest(int userId) {
        var value = new JSONObject();
        value.put(ChatMessagesConstants.USER_ID.value(), userId);
        var data = new JSONObject();
        data.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.RESUME_STREAM.value());
        data.put(MessagesTopLevelConstants.VALUE.value(), value);
        return data;
    }

    public static JSONObject createLeaveStreamRequest(int userId) {
        var value = new JSONObject();
        value.put(ChatMessagesConstants.USER_ID.value(), userId);
        var data = new JSONObject();
        data.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.LEAVE_STREAM.value());
        data.put(MessagesTopLevelConstants.VALUE.value(), value);
        return data;
    }

    public static JSONObject createGetUserSongsData(int userId) {
        var value = new JSONObject();
        value.put(ChatMessagesConstants.USER_ID.value(), userId);
        var data = new JSONObject();
        data.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.GET_USER_SONGS.value());
        data.put(MessagesTopLevelConstants.VALUE.value(), value);
        return data;
    }
}
