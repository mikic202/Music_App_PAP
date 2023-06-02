package client.ServerConnectionConstants;

public enum ChatMessagesConstants {
    USER_ID("user_id"), USERNAME("username"), EMAIL("email"), CONVERSATION_ID("conversation_id");

    private String _value;

    ChatMessagesConstants(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
