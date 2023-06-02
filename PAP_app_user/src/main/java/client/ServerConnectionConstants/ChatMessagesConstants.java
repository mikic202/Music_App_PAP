package client.ServerConnectionConstants;

public enum ChatMessagesConstants {
    USER_ID("user_id"), USERNAME("username"), EMAIL("email"), CONVERSATION_ID("conversation_id"), MESSAGE_TEXT("text"),
    MESSAGE_SENDER_ID("sender_id"), CONVERSATION_NAME("name"), USERS_IN_CONVERSATION("users");

    private String _value;

    ChatMessagesConstants(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
