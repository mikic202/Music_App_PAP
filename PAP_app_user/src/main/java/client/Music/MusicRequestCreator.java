package client.Music;

import org.json.JSONObject;

public class MusicRequestCreator {

    public static JSONObject createStartStreamRequest(int userId, int chatId, int songId) 
    {
        var value = new JSONObject();
        value.put("user_id", userId);
        value.put("chat_id", chatId);
        value.put("song_id", songId);
        var data = new JSONObject();
        data.put("type", MusicRequestTypes.START_STREAM.value());
        data.put("value", value);
        return data;
    }

    public static JSONObject createTerminateStreamRequest(int userId) 
    {
        var value = new JSONObject();
        value.put("user_id", userId);
        var data = new JSONObject();
        data.put("type", MusicRequestTypes.TERMINATE_STREAM.value());
        data.put("value", value);
        return data;
    }
    public static JSONObject createCheckIfPlayingRequest(int chatId)
    {
        var value = new JSONObject();
        value.put("chat_id", chatId);
        var data = new JSONObject();
        data.put("type", MusicRequestTypes.CHECK_IF_PLAYING.value());
        data.put("value", value);
        return data;
    }

    public static JSONObject createJoinPlayingStreamRequest(int userId, int chatId)
    {
        var value = new JSONObject();
        value.put("user_id", userId);
        value.put("chat_id", chatId);
        var data = new JSONObject();
        data.put("type", MusicRequestTypes.JOIN_PLAYING_STREAM.value());
        data.put("value", value);
        return data;
    }
    public static JSONObject createPauseRequest(int userId)
    {
        var value = new JSONObject();
        value.put("user_id", userId);
        var data = new JSONObject();
        data.put("type", MusicRequestTypes.PAUSE_STREAM.value());
        data.put("value", value);
        return data;
    }
    public static JSONObject createResumeRequest(int userId)
    {
        var value = new JSONObject();
        value.put("user_id", userId);
        var data = new JSONObject();
        data.put("type", MusicRequestTypes.RESUME_STREAM.value());
        data.put("value", value);
        return data;
    }
    public static JSONObject createLeaveStreamRequest(int userId)
    {
        var value = new JSONObject();
        value.put("user_id", userId);
        var data = new JSONObject();
        data.put("type", MusicRequestTypes.LEAVE_STREAM.value());
        data.put("value", value);
        return data;
    }
}
