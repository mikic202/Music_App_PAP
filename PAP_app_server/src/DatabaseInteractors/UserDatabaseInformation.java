package DatabaseInteractors;

public enum UserDatabaseInformation {
    USER_TABLE("users"),
    USER_CONVERSATION_TABLE("user_conversation_relation"), ID_COLUMN("user_id"), USERNAME_COLUMN("username"),
    EMAIL_COLUMN("email"), PASSWORD_COLUMN("password");

    private String _value;

    UserDatabaseInformation(String value) {
        _value = value;
    }

    String value() {
        return _value;
    }
}
