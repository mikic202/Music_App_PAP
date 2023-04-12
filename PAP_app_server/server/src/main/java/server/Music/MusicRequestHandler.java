package server.Music;

import org.json.JSONObject;
import javax.sound.sampled.AudioFormat;

public class MusicRequestHandler {

    public static JSONObject processRequests(MusicRequestTypes reqType, JSONObject request)
    {
        return _generateResponse(reqType, request);
    }

    private static JSONObject _startStream(JSONObject request)
    {
        int userId = request.getInt("user_id");
        int chatId = request.getInt("chat_id");
        int songId = request.getInt("song_id");

        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        int port = streamsManagerInstance.startStream(chatId, userId, songId);
        //initiate udp connection for new streamer

        JSONObject valueResult = new JSONObject();
        JSONObject result = new JSONObject();
        result.put("type", MusicRequestTypes.START_STREAM.value());
        valueResult.put("port", port);
        result.put("value", valueResult);
        return result;
    }

    private static JSONObject _terminateStream(JSONObject request)
    {
        int userId = request.getInt("user_id");
        
        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        boolean rtnStopStream = streamsManagerInstance.terminateStream(userId);

        JSONObject valueResult = new JSONObject();
        JSONObject result = new JSONObject();
        result.put("type", MusicRequestTypes.TERMINATE_STREAM.value());
        valueResult.put("outcome", rtnStopStream);
        result.put("value", valueResult);

        return result;
    }

    private static JSONObject _checkIfPlaying(JSONObject request)
    {
        int chatId = request.getInt("chat_id");

        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        boolean streamCreated = streamsManagerInstance.checkIfStreamCreated(chatId);
        boolean streamCurrentlyPlaying = streamsManagerInstance.checkIfStreamCurrentlyPlaying(chatId);

        JSONObject result = new JSONObject();
        result.put("type", MusicRequestTypes.CHECK_IF_PLAYING.value());

        JSONObject valueResult = new JSONObject();
        valueResult.put("created", streamCreated);
        valueResult.put("playing", streamCurrentlyPlaying);

        JSONObject formatParams = new JSONObject();
        AudioFormat format = streamsManagerInstance.getFormat(chatId);

        if (format == null)
        {
            valueResult.put("format_available", 0);
        }
        else
        {
            valueResult.put("format_available", 1);

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
        result.put("value", valueResult);

        return result;
    }

    private static JSONObject _joinPlayingStream(JSONObject request) 
    {
        int userId = request.getInt("user_id");
        int chatId = request.getInt("chat_id");

        JSONObject result = new JSONObject();
        result.put("key", MusicRequestTypes.JOIN_PLAYING_STREAM.value());
        JSONObject valueResult = new JSONObject();

        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        int port = streamsManagerInstance.addListenerToCreatedStream(chatId, userId);
        valueResult.put("port", port);
        result.put("value", valueResult);

        return result;
    }

    private static JSONObject _pauseStream(JSONObject request)
    {
        int userId = request.getInt("user_id");
        
        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        boolean rtnPauseStream = streamsManagerInstance.pauseStream(userId);

        JSONObject valueResult = new JSONObject();
        JSONObject result = new JSONObject();
        result.put("type", MusicRequestTypes.PAUSE_STREAM.value());
        valueResult.put("outcome", rtnPauseStream);
        result.put("value", valueResult);

        return result;
    }

    private static JSONObject _resumeStream(JSONObject request) {
        int userId = request.getInt("user_id");
        
        MusicStreamsManager streamsManagerInstance = MusicStreamsManager.getInstance();
        boolean rtnResumeStream = streamsManagerInstance.resumeStream(userId);

        JSONObject valueResult = new JSONObject();
        JSONObject result = new JSONObject();
        result.put("type", MusicRequestTypes.RESUME_STREAM.value());
        valueResult.put("outcome", rtnResumeStream);
        result.put("value", valueResult);

        return result;
    }

    private static JSONObject _generateResponse(MusicRequestTypes reqType, JSONObject request) {
        JSONObject response = new JSONObject();
        switch (reqType) {
            case START_STREAM:
                response = _startStream(request);
                break;
            case TERMINATE_STREAM:
                response = _terminateStream(request);
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
        }
        return response;
    }
}
