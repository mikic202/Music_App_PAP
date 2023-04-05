package Music;

import server.Music.MusicStreamer;
import java.util.Hashtable;
import server.Chat.RequestTypes;

import org.json.JSONObject;

import java.net.InetAddress;
import java.util.Enumeration;


public final class MusicStreamsManager {

    // The field must be declared volatile so that double check lock would work
    // correctly.
    private static volatile MusicStreamsManager instance;

    // chat ID binded to Music Streamer
    private Hashtable<Integer, MusicStreamer> streamerTable;

    // ports table with list of ports with statuses
    private Hashtable<Integer, Boolean> portsTable;

    private MusicStreamsManager()
    {
        portsTable.put(4445, false);
    }


    public static MusicStreamsManager getInstance() {
        // The approach taken here is called double-checked locking (DCL). It
        // exists to prevent race condition between multiple threads that may
        // attempt to get MusicStreamsManager instance at the same time, creating separate
        // instances as a result.

        MusicStreamsManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized(MusicStreamsManager.class) {
            if (instance == null) {
                instance = new MusicStreamsManager();
            }
            return instance;
        }
    }

    public boolean handleRequest(RequestTypes reqType, JSONObject request)
    {
        switch(reqType)
        {
            case START_STREAM:
                int user_id = Integer.parseInt(request.getString("user_id"));
                int chat_id = Integer.parseInt(request.getString("chat_id"));
                Enumeration<Integer> e = portsTable.keys();
                int freePort = -1;
                while (e.hasMoreElements())
                {
                    int key = e.nextElement();
                    if(portsTable.get(key) == false)
                    {
                        freePort = key;
                        break;
                    }
                }
                streamerTable.put(chat_id, new MusicStreamer(freePort, user_id));
                break;
            case STOP_STREAM:
            //todo
                break;
        }
    }

    private boolean _joinStream(int chatId, InetAddress ipAddress)
    {
        
    }

    private boolean _startStream(int chatId, InetAddress ipAddress)
    {

    }

    private boolean _handleUserJoined()
    {

    }
}