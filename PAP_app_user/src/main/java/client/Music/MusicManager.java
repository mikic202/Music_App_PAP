package client.Music;

import client.Music.MusicClient;
import client.Music.MusicAccessors;
import client.ServerConnector.ServerConnector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;

import org.json.JSONObject;

// @TODO still thinking about this interface
public class MusicManager
{

    private MusicClient musicClient;
    private MusicAccessors musicAccessors;

    int userId = -1;
    String playingSongId;
    AudioFormat format = null;

    public MusicManager(ServerConnector serverConnector, int userId)
    {
        this.musicAccessors = new MusicAccessors(serverConnector);
        this.userId = userId;
    }

    public boolean startStream(int chatId, int songId)
    {
        JSONObject response = musicAccessors.sendStartStream(userId, chatId, songId);
        JSONObject value = response.getJSONObject("value");

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
            //TODO handle other formats
            return false;
        }
        this.format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);

        int port = value.getInt("port");

        musicClient = new MusicClient(format, port);
        musicClient.start();
        return true;

    }

    public int checkIfPlaying(int chatId)
    {
        JSONObject response = musicAccessors.sendCheckIfPlaying(chatId);
        JSONObject value = response.getJSONObject("value");
        boolean streamCreated = value.getBoolean("created");
        boolean streamPlaying = value.getBoolean("playing");
        boolean formatAvailable = value.getBoolean("format_available");
        if (!formatAvailable)
        {
            return -1;
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
            //TODO handle other formats
            return -1;
        }
        this.format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);

        return songId;
    }

    public boolean joinPlayingStream(int chatId)
    {
        JSONObject response = musicAccessors.sendJoinPlayingStream(userId, chatId);
        int port = response.getJSONObject("value").getInt("port");
        if (port != 0)
        {
            musicClient = new MusicClient(format, port);
            musicClient.start();
            return true;
        }
        return false;
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
        return outcome;
    }

    public boolean resumeStream()
    {
        JSONObject response = musicAccessors.sendResume(userId);
        boolean outcome = response.getJSONObject("value").getBoolean("outcome");
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

    private void terminatePlayer()
    {
        musicClient.terminateReceiving();
        musicClient = null;
    }
}
