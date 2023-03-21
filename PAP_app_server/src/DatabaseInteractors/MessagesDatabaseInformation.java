package DatabaseInteractors;

public enum MessagesDatabaseInformation {
    MESSAGES_TABLE("messages"),
    MESSAGE_COLUMN("text"), ID_COLUMN("message_id"), SENDER_COLUMN("sendr_id"),
    CONVERSATION_COLUMN("conversation_id"), DATE_COLUMN("creation_date");

    private String _value;

    MessagesDatabaseInformation(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
