package client.GUI.guiListeners;

public class ChatContentsUpdater {
    private void updateChat(ArrayList<JSONObject> newMessages) {
        messagesArea.removeAll();
        for (JSONObject message : newMessages) {
            if (message.getInt("is_image") == 1) {
                addImage(message);
            } else {
                addTextMessage(message);
            }
        }
        try {
            chatGuiUpdater.call();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
