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

    private int nextPort = 60001;

    private MusicStreamsManager(){}


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

    //returns port that the stream will be streamed from
    public int startStream(int chatId, int initiatorUserId)
    {
        Enumeration<Integer> e = portsTable.keys();
        int freePort = 0;
        while(e.hasMoreElements())
        {
            int key = e.nextElement();
            if(portsTable.get(key) == false)
            {
                freePort = key;
                portsTable.put(key, true); //mark port occupied
                break;
            }
        }
        if (freePort == 0)
        {
            freePort = nextPort;       //create new port if none is free or created
            portsTable.put(nextPort, true); //mark port occupied
            nextPort += 1;
        }
        streamerTable.put(chatId, new MusicStreamer(freePort, initiatorUserId));

        return freePort;
    }

    public int joinStream(int chatId, int userId, InetAddress ipAddress, int port)
    {
        MusicStreamer bindedStreamer = streamerTable.get(chatId);
        synchronized (bindedStreamer)
        {
            bindedStreamer.addListenerToRunningStream(userId, ipAddress, port);
        }
    }

}