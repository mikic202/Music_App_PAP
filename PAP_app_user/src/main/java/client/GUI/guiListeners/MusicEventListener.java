package client.GUI.guiListeners;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import client.Chat.Chat;
import client.ServerConnector.ServerConnector;
import client.login_and_account_accessors.AccountChangeRequestAccessors;
import client.Music.MusicManager;
import client.Music.MusicManager.EStreamStatus;

public class MusicEventListener implements ActionListener {

    private static MusicManager musicManagerInstance;
    private static int chosenSongId = -1;
    private static int chosenChatId = -1;

    public MusicEventListener(ServerConnector serverConnector, int userId) {
        musicManagerInstance = new MusicManager(serverConnector, userId);
    }

    public synchronized void onChatIdChange(int chatId)
    {
        chosenChatId = chatId;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String text = new String();
        if(event.getSource() instanceof JButton) {
            text = ((JButton) event.getSource()).getText();
        }
        else if()
        {
            
        }

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
        }
    }

    private void onEventPause()
    {
        if(!musicManagerInstance.stopPlaying())
        {
            //TODO error msg
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

        //TODO add popup
        
    }

    private void onEventStopStream()
    {
        boolean response = musicManagerInstance.pauseStream();
        if(!response)
        {
            //TODO add popup
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
            popupText = "Chat not chosen!"
        }
    }

}
