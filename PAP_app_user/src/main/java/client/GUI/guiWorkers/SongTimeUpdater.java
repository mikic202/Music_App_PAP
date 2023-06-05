package client.GUI.guiWorkers;
import javax.swing.SwingWorker;
import client.Music.MusicManager;
import java.util.concurrent.Callable;

public class SongTimeUpdater extends SwingWorker<Boolean, Void> {

    private Callable<Void> songTimeUpdate;

    public SongTimeUpdater(Callable<Void> songTimeUpdate) {
        this.songTimeUpdate = songTimeUpdate;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        while (true) {
            songTimeUpdate.call();
            Thread.sleep(500);
        }
    }
}
