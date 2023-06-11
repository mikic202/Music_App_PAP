package server.ServerConnectionConstants;

public enum LoggingMessagesConstants {
    PASSWORD("password"), OLD_PASSWORD("old_password"), NEW_PASSWORD("new_password");

    private String _value;

    LoggingMessagesConstants(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}