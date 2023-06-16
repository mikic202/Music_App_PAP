package server.Music;

import org.json.JSONArray;
import org.json.JSONObject;

import server.DatabaseInteractors.FileDataAccesor;
import javax.sound.sampled.AudioFormat;

import java.util.Enumeration;
import java.util.Hashtable;
import server.ServerConnectionConstants.MessagesTopLevelConstants;
import server.ServerConnectionConstants.ChatMessagesConstants;

public class MusicRequestHandler {

    public static JSONObject processRequests(MusicRequestTypes reqType, JSONObject request) {
        return procesRequests(reqType, request);
    }

    private static JSONObject _startStream(JSONObject request) {
        int userId = request.getInt(ChatMessagesConstants.USER_ID.value());
        int chatId = request.getInt("chat_id");
        int songId = request.getInt("song_id");

        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        int port = streamsManagerInstance.startStream(chatId, userId, songId);

        JSONObject valueResult = new JSONObject();
        JSONObject result = new JSONObject();
        result.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.START_STREAM.value());
        valueResult.put("port", port);
        System.out.println("got out of start stream method");
        ;
        if (port > 0) {
            JSONObject formatParams = new JSONObject();
            AudioFormat format = streamsManagerInstance.getFormat(chatId);
            float sampleRate = format.getSampleRate();
            int sampleSizeInBits = format.getSampleSizeInBits();
            int channels = format.getChannels();
            String encodingStr = format.getEncoding().toString(); // example "pcm_signed"
            boolean bigEndian = format.isBigEndian();
            int lengthInBytes = streamsManagerInstance.getLengthInBytes(chatId);
            formatParams.put("sample_rate", sampleRate);
            formatParams.put("sample_size", sampleSizeInBits);
            formatParams.put("channels", channels);
            formatParams.put("encoding", encodingStr);
            formatParams.put("big_endian", bigEndian);
            formatParams.put("length", lengthInBytes);

            valueResult.put("format", formatParams);
        }

        result.put(MessagesTopLevelConstants.VALUE.value(), valueResult);
        return result;
    }

    private static JSONObject _checkIfPlaying(JSONObject request) {
        int chatId = request.getInt("chat_id");

        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        boolean streamCreated = streamsManagerInstance.checkIfStreamCreated(chatId);
        boolean streamCurrentlyPlaying = streamsManagerInstance.checkIfStreamCurrentlyPlaying(chatId);

        JSONObject result = new JSONObject();
        result.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.CHECK_IF_PLAYING.value());

        JSONObject valueResult = new JSONObject();
        valueResult.put("created", streamCreated);
        valueResult.put("playing", streamCurrentlyPlaying);

        JSONObject formatParams = new JSONObject();
        AudioFormat format = streamsManagerInstance.getFormat(chatId);
        int songId = streamsManagerInstance.getPlayingSongId(chatId);

        if (format == null) {
            valueResult.put("format_available", false);
        } else {
            valueResult.put("format_available", true);

            float sampleRate = format.getSampleRate();
            int sampleSizeInBits = format.getSampleSizeInBits();
            int channels = format.getChannels();
            String encodingStr = format.getEncoding().toString(); // example "pcm_signed"
            boolean bigEndian = format.isBigEndian();
            int lengthInBytes = streamsManagerInstance.getLengthInBytes(chatId);
            formatParams.put("sample_rate", sampleRate);
            formatParams.put("sample_size", sampleSizeInBits);
            formatParams.put("channels", channels);
            formatParams.put("encoding", encodingStr);
            formatParams.put("big_endian", bigEndian);
            formatParams.put("length", lengthInBytes);
            formatParams.put("song_id", songId);
            valueResult.put("format", formatParams);
        }
        result.put(MessagesTopLevelConstants.VALUE.value(), valueResult);

        return result;
    }

    private static JSONObject _joinPlayingStream(JSONObject request) {
        int userId = request.getInt(ChatMessagesConstants.USER_ID.value());
        int chatId = request.getInt("chat_id");

        JSONObject result = new JSONObject();
        result.put("key", MusicRequestTypes.JOIN_PLAYING_STREAM.value());
        JSONObject valueResult = new JSONObject();

        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        int port = streamsManagerInstance.addListenerToCreatedStream(chatId, userId);
        valueResult.put("port", port);
        result.put(MessagesTopLevelConstants.VALUE.value(), valueResult);

        return result;
    }

    private static JSONObject _pauseStream(JSONObject request) {
        int userId = request.getInt(ChatMessagesConstants.USER_ID.value());

        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        boolean rtnPauseStream = streamsManagerInstance.pauseStream(userId);

        JSONObject valueResult = new JSONObject();
        JSONObject result = new JSONObject();
        result.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.PAUSE_STREAM.value());
        valueResult.put(MessagesTopLevelConstants.OUTCOME.value(), rtnPauseStream);
        result.put(MessagesTopLevelConstants.VALUE.value(), valueResult);

        return result;
    }

    private static JSONObject _resumeStream(JSONObject request) {
        int userId = request.getInt(ChatMessagesConstants.USER_ID.value());

        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        boolean rtnResumeStream = streamsManagerInstance.resumeStream(userId);

        JSONObject valueResult = new JSONObject();
        JSONObject result = new JSONObject();
        result.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.RESUME_STREAM.value());
        valueResult.put(MessagesTopLevelConstants.OUTCOME.value(), rtnResumeStream);
        result.put(MessagesTopLevelConstants.VALUE.value(), valueResult);

        return result;
    }

    private static JSONObject _leaveStream(JSONObject request) {
        int userId = request.getInt(ChatMessagesConstants.USER_ID.value());

        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        boolean removeListenerFromCreatedStream = streamsManagerInstance.removeListenerFromCreatedStream(userId);

        JSONObject valueResult = new JSONObject();
        JSONObject result = new JSONObject();
        result.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.LEAVE_STREAM.value());
        valueResult.put(MessagesTopLevelConstants.OUTCOME.value(), removeListenerFromCreatedStream);
        result.put(MessagesTopLevelConstants.VALUE.value(), valueResult);

        return result;
    }

    private static JSONObject _getUserSongs(JSONObject request) {
        int userId = request.getInt(ChatMessagesConstants.USER_ID.value());
        JSONObject result = new JSONObject();

        JSONArray songsList = new JSONArray();

        Hashtable<String, Integer> songs = FileDataAccesor.getUserFiles(userId);
        Enumeration<String> e = songs.keys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            int id = songs.get(key);
            JSONObject data = new JSONObject();
            data.put("name", key);
            data.put("id", id);
            songsList.put(data);
        }

        result.put(MessagesTopLevelConstants.TYPE.value(), MusicRequestTypes.GET_USER_SONGS.value());
        result.put(MessagesTopLevelConstants.VALUE.value(), songsList);

        return result;
    }

    private static JSONObject procesRequests(MusicRequestTypes reqType, JSONObject request) {
        JSONObject response = new JSONObject();
        switch (reqType) {
            case START_STREAM:
                response = _startStream(request);
                System.out.print("response returned");
                break;
            case CHECK_IF_PLAYING:
                response = _checkIfPlaying(request);
                break;
            case JOIN_PLAYING_STREAM:
                response = _joinPlayingStream(request);
                break;
            case PAUSE_STREAM:
                response = _pauseStream(request);
                break;
            case RESUME_STREAM:
                response = _resumeStream(request);
                break;
            case LEAVE_STREAM:
                response = _leaveStream(request);
                break;
            case GET_USER_SONGS:
                response = _getUserSongs(request);
                break;
        }
        return response;
    }
}
