package server.ServerConnectionConstants;

public enum ChatMessagesConstants {
    USER_ID("user_id"), USERNAME("username"), EMAIL("email"), CONVERSATION_ID("conversation_id"), MESSAGE_TEXT("text"),
    MESSAGE_SENDER_ID("sender_id"), CONVERSATION_NAME("name"), USERS_IN_CONVERSATION("users"),
    LATEST_MESSAGE("latest_message"), IMAGE_MESSAGE("image"), IMAGE_MESSAGE_FORMAT("format"),
    CONVERSATION_CODE("conversation_code"), NEW_CONVERSATION_NAME("conversation_name");

    private String _value;

    ChatMessagesConstants(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
