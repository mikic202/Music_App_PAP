package client.Music;

import client.Music.MusicClient;
import client.Music.MusicAccessors;
import client.ServerConnector.ServerConnector;

import javax.sound.sampled.AudioFormat;
import client.Music.MusicPlayer.StreamStatusCallback;

import org.json.JSONObject;

// @TODO still thinking about this interface
public class MusicManager
{
    enum EStreamStatus
    {
        STREAM_INVALID,
        STREAM_PAUSED,
        STREAM_PLAYING
    }

    private MusicClient musicClient;
    private MusicAccessors musicAccessors;

    private int userId = -1;
    private int playingSongId;
    private AudioFormat format = null;
    private long songLengthInBytes = 0;
    private EStreamStatus currentStreamStatus = EStreamStatus.STREAM_INVALID;
    private int currentChatId = -1;

    public StreamStatusCallback streamStatusCb = () -> {this.checkIfStreamPaused();};

    public MusicManager(ServerConnector serverConnector, int userId)
    {
        this.musicAccessors = new MusicAccessors(serverConnector);
        this.userId = userId;
    }

    public boolean startStream(int chatId, int songId)
    {
        JSONObject response = musicAccessors.sendStartStream(userId, chatId, songId);
        JSONObject value = response.getJSONObject("value");

        int port = value.getInt("port");
        if (port == 0)
        {
            return false;
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
        if (encodingStr.equals("PCM_SIGNED"))
        {
            System.out.print("pcm signed received");
            signed = true;
        }
        else if (encodingStr.equals("PCM_UNSIGNED"))
        {
            signed = false;
        }
        else
        {
            return false;
        }
        this.format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        this.playingSongId = songId;
        this.songLengthInBytes = lengthInBytes;
        this.currentChatId = chatId;

        musicClient = new MusicClient(format, port, true, streamStatusCb);
        musicClient.run();

        currentStreamStatus = EStreamStatus.STREAM_PLAYING;
        
        return true;
    }

    public EStreamStatus joinPlayingStream(int chatId)
    {
        EStreamStatus streamStatus = getPlayingStreamInfo(chatId);
        // TODO this should be handled in gui
        if(streamStatus != EStreamStatus.STREAM_INVALID)
        {
            JSONObject response = musicAccessors.sendJoinPlayingStream(userId, chatId);
            int port = response.getJSONObject("value").getInt("port");
            if (port != 0)
            {
                boolean startNow = (streamStatus == EStreamStatus.STREAM_PLAYING) ? true : false;
                musicClient = new MusicClient(format, port, startNow, streamStatusCb);
                musicClient.run();
                currentStreamStatus = streamStatus;
                this.currentChatId = chatId;
            }
            else
            {
                streamStatus = EStreamStatus.STREAM_INVALID;
            }
        }
        return streamStatus;
    }

    public boolean terminateStream()
    {
        if (musicClient == null)
        {
            return false;
        }
        JSONObject response = musicAccessors.sendTerminateStream(userId);
        boolean outcome = response.getJSONObject("value").getBoolean("outcome");
        if(outcome)
        {
            terminatePlayer();
            initializeMusicManager();
        }
        return outcome;
    }

    public boolean pauseStream()
    {
        if (musicClient == null)
        {
            return false;
        }
        JSONObject response = musicAccessors.sendPause(userId);
        boolean outcome = response.getJSONObject("value").getBoolean("outcome");
        if(outcome)
        {
            currentStreamStatus = EStreamStatus.STREAM_PAUSED;
        }
        return outcome;
    }

    public boolean resumeStream()
    {
        JSONObject response = musicAccessors.sendResume(userId);
        boolean outcome = response.getJSONObject("value").getBoolean("outcome");
        if(outcome)
        {
            currentStreamStatus = EStreamStatus.STREAM_PLAYING;
        }
        return outcome;
    }

    public boolean stopPlaying()
    {
        if(musicClient == null)
        {
            return false;
        }
        musicClient.stopPlaying();
        return true;
    }

    public boolean resumePlaying()
    {
        if(musicClient == null)
        {
            return false;
        }
        musicClient.resumePlaying();
        return true;
    }

    public long getSongLengthInBytes()
    {
        return songLengthInBytes;
    }
    
    public int getPlayingSongId()
    {
        return playingSongId;
    }

    public synchronized EStreamStatus getPlayingStreamInfo(int chatId)
    {
        JSONObject response = musicAccessors.sendCheckIfPlaying(chatId);
        JSONObject value = response.getJSONObject("value");
        boolean streamCreated = value.getBoolean("created");
        boolean streamPlaying = value.getBoolean("playing");
        boolean formatAvailable = value.getBoolean("format_available");
        if (!formatAvailable || !streamCreated)
        {
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
        if (encodingStr == "pcm_signed")
        {
            signed = true;
        }
        else if (encodingStr == "pcm_unsigned")
        {
            signed = false;
        }
        else
        {
            //Unsupported file format
            return EStreamStatus.STREAM_INVALID;
        }
        this.format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        this.playingSongId = songId;
        this.songLengthInBytes = lengthInBytes;
        this.currentChatId = chatId;

        return streamPlaying ? EStreamStatus.STREAM_PLAYING : EStreamStatus.STREAM_PAUSED;
    }

    public synchronized void checkIfStreamPaused()
    {
        boolean streamPaused = getPlayingStreamInfo(currentChatId) == EStreamStatus.STREAM_PAUSED ? true : false;
        if(streamPaused)
        {
            musicClient.stopPlaying();
        }
    }

    public EStreamStatus checkCurrentStreamStatus()
    {
        return currentStreamStatus;
    }

    private void terminatePlayer()
    {
        musicClient.terminateReceiving();
        musicClient = null;
    }

    private void initializeMusicManager()
    {
        userId = -1;
        playingSongId = 0;
        format = null;
        songLengthInBytes = 0;
        currentStreamStatus = EStreamStatus.STREAM_INVALID;
    }
}