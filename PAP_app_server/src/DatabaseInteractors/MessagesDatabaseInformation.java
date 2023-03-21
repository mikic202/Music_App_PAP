package DatabaseInteractors;

public enum MessagesDatabaseInformation {
    MESSAGES_TABLE("messages"),
    MESSAGE_COLUMN("user_conversation_relation"), ID_COLUMN("message_id"), SENDER_COLUMN("username"),
    CONVERSATION_COLUMN("email"), DATE_COLUMN("password");

    private String _value;

    MessagesDatabaseInformation(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
