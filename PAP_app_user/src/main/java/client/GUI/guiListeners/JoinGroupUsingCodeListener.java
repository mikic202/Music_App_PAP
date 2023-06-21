package client.GUI.guiListeners;

import java.util.concurrent.Callable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import client.Chat.Chat;

public class JoinGroupUsingCodeListener implements ActionListener {

    public JoinGroupUsingCodeListener(JTextField codeArea, Callable<Void> guiUpdater) {
        this.codeArea = codeArea;
        this.guiUpdater = guiUpdater;
    }

    public JoinGroupUsingCodeListener(Chat chat, JTextField codeArea, Callable<Void> guiUpdater) {
        this.chat = chat;
        this.codeArea = codeArea;
        this.guiUpdater = guiUpdater;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (codeArea.getText().trim().equals("")) {
            return;
        }
        new Thread(new SendMessageGuiUpdater()).start();
    }

    class SendMessageGuiUpdater implements Runnable {

        @Override
        public void run() {
            chat.joinConversationUsingCode(codeArea.getText().trim());
            codeArea.setText("");
            try {
                guiUpdater.call();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    Chat chat;
    JTextField codeArea;
    Callable<Void> guiUpdater;
}
