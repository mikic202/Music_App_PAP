package client.ServerConnectionConstants;

public enum ChatMessagesConstants {
    VALUE("value"), TYPE("type");

    private String _value;

    ChatMessagesConstants(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
