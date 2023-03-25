package DatabaseInteractors;

public enum ConversationDatabsaeInformation {
    CONVERSATION_TABLE("conversation"),
    NAME_COLUMN("text"), ID_COLUMN("conversation_id"), USER_COLUMN("user_id"),
    CONVERSATION_COLUMN("conversation_id"), NUMBER_OF_USERS_COLUMN("creation_date");

    private String _value;

    ConversationDatabsaeInformation(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
