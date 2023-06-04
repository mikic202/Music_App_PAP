package client.ServerConnectionConstants;

public enum LoggingMessagesConstants {
    PASSWORD("password");

    private String _value;

    LoggingMessagesConstants(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}