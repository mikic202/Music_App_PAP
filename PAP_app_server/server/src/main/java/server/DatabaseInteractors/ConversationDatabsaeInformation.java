package DatabaseInteractors;

public enum ConversationDatabsaeInformation {
    CONVERSATION_TABLE("conversation"),
    NAME_COLUMN("name"), ID_COLUMN("conversation_id"), NUMBER_OF_USERS_COLUMN("number_of_users"),
    USER_COLUMN("user_id");

    private String _value;

    ConversationDatabsaeInformation(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
