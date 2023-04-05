package server.DatabaseInteractors;

public enum DatabseInformation {
    PASSWORD(""), URL("jdbc:mysql://localhost:3306/PAP_app"), USER("root"), USER_TABLE("users"),
    MESSAGES_TABLE("messages"), CONVERSATION_TABLE("conversations"), FILES_TABLE("files"),
    USER_CONVERSATION_TABLE("user_conversation_relation");

    private String _value;

    DatabseInformation(String value) {
        _value = value;
    }

    String value() {
        return _value;
    }
}
