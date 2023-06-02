package client.ServerConnectionConstants;

public enum MessagesTopLevelConstants {
    VALUE("value"), TYPE("type");

    private String _value;

    MessagesTopLevelConstants(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
