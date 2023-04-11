package client.Music;

import client.Music.MusicClient;
import client.Music.MusicAccessors;


// @TODO still thinking about this interface
public class MusicClientManager
{

    private static volatile MusicClientManager instance;

    private MusicClient musicClient;

    private MusicClientManager(){}

    public static MusicClientManager getInstance() {
        // The approach taken here is called double-checked locking (DCL). It
        // exists to prevent race condition between multiple threads that may
        // attempt to get MusicClientManager instance at the same time, creating separate
        // instances as a result.

        MusicClientManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized(MusicClientManager.class) {
            if (instance == null) {
                instance = new MusicClientManager();
            }
            return instance;
        }
    }

}
