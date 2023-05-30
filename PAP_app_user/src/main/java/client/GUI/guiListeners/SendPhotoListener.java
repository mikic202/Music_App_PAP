package client.GUI.guiListeners;

import java.awt.Color;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.json.JSONObject;

import client.Chat.Chat;
import client.GUI.LeftChatPanel;

public class SendPhotoListener implements ActionListener {

    public SendPhotoListener(Chat chat, StringBuilder imagePath, JLabel pathLable) {
        this.chat = chat;
        this.imagePath = imagePath;
        this.pathLable = pathLable;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (imagePath.toString().trim().equals("")) {
            return;
        }
        new Thread(new SendMessageGuiUpdater()).start();
    }

    class SendMessageGuiUpdater implements Runnable {

        @Override
        public void run() {
            chat.sendImage(imagePath.toString());
            imagePath.delete(0, imagePath.length());
            pathLable.setText(imagePath
                    .substring(imagePath.lastIndexOf("\\") + 1, imagePath.length()));
        }

    }

    Chat chat;
    StringBuilder imagePath;
    JLabel pathLable;
}
