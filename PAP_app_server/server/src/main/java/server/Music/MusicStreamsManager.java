package server.Music;

import java.util.Hashtable;

import server.Music.MusicStreamer;
import java.util.Enumeration;

import server.DatabaseInteractors.FileDataAccesor;
import server.DatabaseInteractors.FileDatabsaeInformation;

import javax.sound.sampled.AudioFormat;

public final class MusicStreamsManager {

    // The field must be declared volatile so that double check lock would work
    // correctly.
    private static volatile MusicStreamsManager instance;

    // chat ID binded to Music Streamer
    private Hashtable<Integer, MusicStreamer> streamerTable = new Hashtable<Integer, MusicStreamer>();

    // ports table with list of ports with statuses
    private Hashtable<Integer, Boolean> portsTable = new Hashtable<Integer, Boolean>();

    // userId binded to chatId that it is listening
    private Hashtable<Integer, Integer> userChatTable = new Hashtable<Integer, Integer>();

    private int nextPort = 60001;

    private MusicStreamsManager() {
    }

    public static MusicStreamsManager getInstance() {
        // The approach taken here is called double-checked locking (DCL). It
        // exists to prevent race condition between multiple threads that may
        // attempt to get MusicStreamsManager instance at the same time, creating
        // separate
        // instances as a result.

        MusicStreamsManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized (MusicStreamsManager.class) {
            if (instance == null) {
                instance = new MusicStreamsManager();
            }
            return instance;
        }
    }

    // returns port that the stream will be streamed from
    public int startStream(int chatId, int initiatorUserId, int songId) {
        System.out.print("start stream called");
        int freePort = 0;
        if (streamerTable != null) {
            if (streamerTable.contains(chatId)) {
                if(streamerTable.get(chatId).getInitiator() == initiatorUserId)
                {
                    // stream already started on this chat
                    System.out.println("stream already started on this chat");
                    return freePort;
                }
            }
        }
        Enumeration<Integer> e = portsTable.keys();
        while (e.hasMoreElements()) {
            int key = e.nextElement();
            if (portsTable.get(key) == false) {
                freePort = key;
                portsTable.put(key, true); // mark port occupied
                break;
            }
        }
        if (freePort == 0) {
            freePort = nextPort; // create new port if none is free or created
            portsTable.put(nextPort, true); // mark port occupied
            nextPort += 1;
        }

        Hashtable<String, String> querryResult = FileDataAccesor.getData(FileDatabsaeInformation.ID_COLUMN.value(), songId);
        String filePath = querryResult.get("file_path");

        MusicStreamer createdStreamer = new MusicStreamer(freePort, initiatorUserId, filePath, songId);
        streamerTable.put(chatId, createdStreamer);
        userChatTable.put(initiatorUserId, chatId);

        if(!createdStreamer.checkIfSupportedEncoding())
        {
            terminateStream(initiatorUserId);
            return 0;
        }
        createdStreamer.start();
        
        System.out.println(freePort);
        return freePort;
    }

    public boolean terminateStream(int userId) {
        int chatId = userChatTable.get(userId);
        MusicStreamer stream = streamerTable.get(chatId);
        boolean rtn;
        if (stream == null || stream.getInitiator() != userId)
        {
           return false;
        }
        else
        {
            rtn = stream.terminateStream(userId);
        }

        Hashtable<Integer, Integer> newUserChatTable = new Hashtable<Integer, Integer>(userChatTable);
        Enumeration<Integer> e = userChatTable.keys();
        while (e.hasMoreElements()) {
            int key = e.nextElement();
            if (userChatTable.get(key) == chatId) {
                newUserChatTable.remove(key);
            }
        }
        userChatTable = newUserChatTable;
        streamerTable.remove(chatId);

        return rtn;
    }

    public boolean pauseStream(int userId) {
        int chatId = userChatTable.get(userId);
        MusicStreamer stream = streamerTable.get(chatId);
        if (stream == null) {
            return false;
        }
        return stream.pauseStream(userId);

    }

    public boolean resumeStream(int userId) {
        int chatId = userChatTable.get(userId);
        MusicStreamer stream = streamerTable.get(chatId);
        if (stream == null) {
            return false;
        }

        return stream.resumeStream(userId);
    }

    public boolean checkIfStreamCurrentlyPlaying(int chatId) {
        MusicStreamer stream = streamerTable.get(chatId);
        if (stream == null) {
            return false;
        }
        return !stream.checkIfPaused();
    }

    public boolean checkIfStreamCreated(int chatId) {
        MusicStreamer stream = streamerTable.get(chatId);
        if (stream == null) {
            return false;
        }
        return true;
    }

    public int addListenerToCreatedStream(int chatId, int userId) {
        MusicStreamer bindedStreamer = streamerTable.get(chatId);
        if (bindedStreamer == null) {
            return 0;
        }

        int rtn = bindedStreamer.addListenerToCreatedStream(userId);

        if (rtn != 0) {
            userChatTable.put(userId, chatId);
        }
        return rtn;
    }

    public boolean removeListenerFromCreatedStream(int userId) {
        int chatId = userChatTable.get(userId);
        MusicStreamer stream = streamerTable.get(chatId);
        if (stream == null) {
            return false;
        }
        if (userId == stream.getInitiator()) {
            return terminateStream(userId); // terminate if owner has left
        }
        return stream.removeListenerFromCreatedStream(userId);
    }

    public AudioFormat getFormat(int chatId) {
        MusicStreamer stream = streamerTable.get(chatId);
        if (stream == null) {
            return null;
        }
        return stream.getFormat();
    }

    public int getLengthInBytes(int chatId) {
        MusicStreamer stream = streamerTable.get(chatId);
        if (stream == null) {
            return 0;
        }
        return stream.getLength();
    }

    public int getPlayingSongId(int chatId) {
        MusicStreamer stream = streamerTable.get(chatId);
        if (stream == null) {
            return 0;
        }
        return stream.getPlayingSongId();
    }
}