package client.Music;

import client.Music.MusicClient;
import client.Music.MusicAccessors;
import client.ServerConnector.ServerConnector;

import javax.sound.sampled.AudioFormat;
import client.Music.MusicPlayer.StreamStatusCallback;
import client.ServerConnectionConstants.MessagesTopLevelConstants;
import client.ServerConnectionConstants.ChatMessagesConstants;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import java.util.Hashtable;

public class MusicManager {
    public enum EStreamStatus {
        STREAM_INVALID,
        STREAM_PAUSED,
        STREAM_PLAYING
    }

    private MusicClient musicClient;
    private static MusicAccessors musicAccessors;

    private static int thisUserId = -1;
    private int playingSongId = -1;
    private AudioFormat format = null;
    private int songLengthInBytes = 0;
    private EStreamStatus currentStreamStatus = EStreamStatus.STREAM_INVALID;
    private int currentChatId = -1;
    private static Hashtable<String, Integer> userSongs = new Hashtable<String, Integer>();

    public StreamStatusCallback streamStatusCb = () -> {
        this.checkIfStreamPaused();
    };

    public MusicManager(ServerConnector serverConnector, int userId) {
        musicAccessors = new MusicAccessors(serverConnector);
        thisUserId = userId;
    }

    public static synchronized void updateUserSongsList() {
        /*JSONObject result = musicAccessors.sendGetUserSongs(thisUserId);
        JSONArray songs = result.getJSONArray(MessagesTopLevelConstants.VALUE.value());

        for(int i = 0; i < songs.length(); i++)
        {
            JSONObject data = songs.getJSONObject(i);
            String songName =
            data.getString(ChatMessagesConstants.CONVERSATION_NAME.value());
            int songId = data.getInt("id");
            if(songName != "none")
            {
                userSongs.put(songName, songId);
            }
        }
        */
        userSongs.put("inva", 3);
        userSongs.put("song", 2);
    }

    public static synchronized Hashtable<String, Integer> getUserSongsData() {
        return userSongs;
    }

    public static synchronized int getSongIdByName(String songName) {
        return userSongs.get(songName);
    }

    public synchronized EStreamStatus startStream(int chatId, int songId) {
        if (musicClient != null) {
            if(musicClient.isActive())
            {
                musicAccessors.sendLeaveStream(thisUserId);
                musicClient.terminateReceiving();
            }
        }
        JSONObject response = musicAccessors.sendStartStream(thisUserId, chatId, songId);
        JSONObject value = response.getJSONObject(MessagesTopLevelConstants.VALUE.value());

        int port = value.getInt("port");
        if (port == 0) {
            return EStreamStatus.STREAM_INVALID;
        } else if (port == -1) {
            return EStreamStatus.STREAM_PAUSED;
        }

        JSONObject jsonFormat = value.getJSONObject("format");

        float sampleRate = jsonFormat.getFloat("sample_rate");
        int sampleSizeInBits = jsonFormat.getInt("sample_size");
        int channels = jsonFormat.getInt("channels");
        boolean bigEndian = jsonFormat.getBoolean("big_endian");
        int lengthInBytes = jsonFormat.getInt("length");
        String encodingStr = jsonFormat.getString("encoding");
        boolean signed = true;
        System.out.println(encodingStr);
        if (encodingStr.equals("PCM_SIGNED")) {
            System.out.print("pcm signed received");
            signed = true;
        } else if (encodingStr.equals("PCM_UNSIGNED")) {
            signed = false;
        } else {
            return EStreamStatus.STREAM_INVALID;
        }
        this.format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        this.playingSongId = songId;
        this.songLengthInBytes = lengthInBytes;
        this.currentChatId = chatId;

        musicClient = new MusicClient(format, port, true, streamStatusCb, lengthInBytes);
        Thread musicClienThread = new Thread(musicClient);
        musicClienThread.start();

        currentStreamStatus = EStreamStatus.STREAM_PLAYING;

        return currentStreamStatus;
    }

    public synchronized EStreamStatus joinPlayingStream(int chatId) {
        if (musicClient != null) {
            if(musicClient.isActive())
            {
                musicAccessors.sendLeaveStream(thisUserId);
                musicClient.terminateReceiving();
            }
        }
        EStreamStatus streamStatus = getPlayingStreamInfo(chatId);
        if (streamStatus != EStreamStatus.STREAM_INVALID) {
            JSONObject response = musicAccessors.sendJoinPlayingStream(thisUserId, chatId);
            int port = response.getJSONObject(MessagesTopLevelConstants.VALUE.value()).getInt("port");
            if (port != 0) {
                boolean startNow = (streamStatus == EStreamStatus.STREAM_PLAYING) ? true : false;
                musicClient = new MusicClient(format, port, startNow, streamStatusCb, songLengthInBytes);
                Thread musicClienThread = new Thread(musicClient);
                musicClienThread.start();
                currentStreamStatus = streamStatus;
                this.currentChatId = chatId;
            } else {
                streamStatus = EStreamStatus.STREAM_INVALID;
            }
        }
        return streamStatus;
    }

    public synchronized boolean leaveStream() {
        if (musicClient == null) {
            return false;
        }
        JSONObject response = musicAccessors.sendLeaveStream(thisUserId);
        boolean outcome = response.getJSONObject(MessagesTopLevelConstants.VALUE.value()).getBoolean("outcome");
        if (outcome) {
            terminatePlayer();
            initializeMusicManager();
        }
        return outcome;
    }

    public synchronized boolean pauseStream() {
        if (musicClient == null) {
            return false;
        }
        JSONObject response = musicAccessors.sendPause(thisUserId);
        boolean outcome = response.getJSONObject(MessagesTopLevelConstants.VALUE.value()).getBoolean("outcome");
        if (outcome) {
            currentStreamStatus = EStreamStatus.STREAM_PAUSED;
        }
        musicClient.stopPlaying();
        return outcome;
    }

    public synchronized boolean resumeStream() {
        JSONObject response = musicAccessors.sendResume(thisUserId);
        boolean outcome = response.getJSONObject(MessagesTopLevelConstants.VALUE.value()).getBoolean("outcome");
        if (outcome) {
            currentStreamStatus = EStreamStatus.STREAM_PLAYING;
        }
        musicClient.resumePlaying();
        return outcome;
    }

    public synchronized boolean stopPlaying() {
        if (musicClient == null) {
            return false;
        }
        musicClient.stopPlaying();
        return true;
    }

    public synchronized boolean resumePlaying() {
        if (musicClient == null) {
            return false;
        }
        musicClient.resumePlaying();
        return true;
    }

    public synchronized long getSongLengthInBytes() {
        return songLengthInBytes;
    }

    public synchronized int getPlayingSongId() {
        return playingSongId;
    }

    public synchronized EStreamStatus getPlayingStreamInfo(int chatId) {
        JSONObject response = musicAccessors.sendCheckIfPlaying(chatId);
        JSONObject value = response.getJSONObject(MessagesTopLevelConstants.VALUE.value());
        boolean streamCreated = value.getBoolean("created");
        boolean streamPlaying = value.getBoolean("playing");
        boolean formatAvailable = value.getBoolean("format_available");
        if (!formatAvailable || !streamCreated) {
            return EStreamStatus.STREAM_INVALID;
        }
        JSONObject format = value.getJSONObject("format");

        float sampleRate = format.getFloat("sample_rate");
        int sampleSizeInBits = format.getInt("sample_size");
        int channels = format.getInt("channels");
        boolean bigEndian = format.getBoolean("big_endian");
        int lengthInBytes = format.getInt("length");
        int songId = format.getInt("song_id");
        String encodingStr = format.getString("encoding");
        boolean signed = true;
        if (encodingStr == "pcm_signed") {
            signed = true;
        } else if (encodingStr == "pcm_unsigned") {
            signed = false;
        } else {
            // Unsupported file format
            return EStreamStatus.STREAM_INVALID;
        }
        this.format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        this.playingSongId = songId;
        this.songLengthInBytes = lengthInBytes;
        this.currentChatId = chatId;

        return streamPlaying ? EStreamStatus.STREAM_PLAYING : EStreamStatus.STREAM_PAUSED;
    }

    public synchronized void checkIfStreamPaused() {
        EStreamStatus status = getPlayingStreamInfo(currentChatId);
        if (status == EStreamStatus.STREAM_PAUSED) {
            musicClient.stopPlaying();
        }
        else if(status == EStreamStatus.STREAM_INVALID)
        {
            musicClient.terminateReceiving();
        }
    }

    public synchronized EStreamStatus checkCurrentStreamStatus() {
        return currentStreamStatus;
    }

    private void terminatePlayer() {
        musicClient.terminateReceiving();
        musicClient = null;
    }

    private void initializeMusicManager() {
        thisUserId = -1;
        playingSongId = 0;
        format = null;
        songLengthInBytes = 0;
        currentStreamStatus = EStreamStatus.STREAM_INVALID;
    }
}