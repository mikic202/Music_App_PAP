package client.GUI.guiListeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import client.Music.MusicManager;

public class SongListSelectionListener implements ListSelectionListener {

    private static int chosenSongId;
    private static String chosenSongName;

    public SongListSelectionListener() {
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (event.getSource() instanceof JList) {
            chosenSongName = ((JList) event.getSource()).getSelectedValue().toString();
            chosenSongId = MusicManager.getSongIdByName(chosenSongName);
            MusicEventListener.onSongIdChange(chosenSongId);
        }
    }
}
