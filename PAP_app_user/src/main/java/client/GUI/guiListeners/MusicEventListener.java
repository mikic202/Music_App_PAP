package client.GUI.guiListeners;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import client.ServerConnector.ServerConnector;
import client.Music.MusicManager;
import client.Music.MusicManager.EStreamStatus;

public class MusicEventListener implements ActionListener {

    private static MusicManager musicManagerInstance;
    private static int chosenSongId = -1;
    private static int chosenChatId = -1;

    public MusicEventListener(ServerConnector serverConnector, int userId) {
        musicManagerInstance = new MusicManager(serverConnector, userId);
    }

    public static synchronized void onChatIdChange(int chatId)
    {
        chosenChatId = chatId;
    }

    public static synchronized void onSongIdChange(int songId)
    {
        chosenSongId = songId;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String text = new String();
        //if(event.getSource() instanceof JButton) {
            text = ((JButton) event.getSource()).getText();
            System.out.println(text);
        //}
        if(text == "Play")
        {
            onEventPlay();
        }
        else if(text == "Pause")
        {
            onEventPause();
        }
        else if(text == "Start stream")
        {
            onEventStartStream();
        }
        else if(text =="Stop stream")
        {
            onEventStopStream();
        }
        else if(text == "Join stream")
        {
            onEventJoinStream();
        }
    }

    private void onEventPlay()
    {
        if(!musicManagerInstance.resumePlaying())
        {
            //TODO error msg
            System.out.println("failed to resume");
        }
    }

    private void onEventPause()
    {
        if(!musicManagerInstance.stopPlaying())
        {
            System.out.println("failed to stop playing");
        }
    }

    private void onEventStartStream()
    {
        String popupText = new String();

        if(chosenChatId == -1 || chosenSongId == -1)
        {
            popupText = "Song or chat not chosen!";
        }
        else
        {
            EStreamStatus response = musicManagerInstance.startStream(chosenChatId, chosenSongId);
    
            switch(response)
            {
            case STREAM_INVALID:
            break;
            case STREAM_PAUSED:
                popupText = "Stream already playing on this channel, and you are not the owner.";
            break;
            case STREAM_PLAYING:
                popupText = "Stream successfully started!";
            break;
            }

        }

        System.out.println(popupText);
        
    }

    private void onEventStopStream()
    {
        boolean response = musicManagerInstance.pauseStream();
        if(!response)
        {
            System.out.println("Stopped not successfull");
        }
    }

    private void onEventJoinStream()
    {
        String popupText = new String();

        if(chosenChatId != -1)
        {
            EStreamStatus response = musicManagerInstance.joinPlayingStream(chosenChatId);

            switch(response)
            {
            case STREAM_INVALID:
            break;
            case STREAM_PAUSED:
                popupText = "Stream already playing on this channel, and you are not the owner.";
            break;
            case STREAM_PLAYING:
                popupText = "Stream successfully started!";
            break;
            }
        }
        else
        {
            popupText = "Chat not chosen!";
        }

        System.out.println(popupText);
    }

}
