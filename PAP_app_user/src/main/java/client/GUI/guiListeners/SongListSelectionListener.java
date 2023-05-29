package client.GUI.guiListeners;


import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import client.GUI.guiListeners.MusicEventListener;

public class SongListSelectionListener implements ListSelectionListener {
    
    private static int chosenSongId;
    private static String chosenSongName;

    public SongListSelectionListener(){}

    @Override
    public void valueChanged(ListSelectionEvent event)
    {
        if(event.getSource() instanceof JList) {
            chosenSongName = ((JList) event.getSource()).getSelectedValue().toString();
            //TODO get song id based on name?
            MusicEventListener.onSongIdChange(chosenSongId);
        }
    }
}

