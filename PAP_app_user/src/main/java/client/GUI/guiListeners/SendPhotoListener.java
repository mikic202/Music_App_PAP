package client.GUI.guiListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import client.Chat.Chat;

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
