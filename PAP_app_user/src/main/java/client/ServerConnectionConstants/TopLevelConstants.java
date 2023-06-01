package client.ServerConnectionConstants;

public enum TopLevelConstants {
    VALUE("value");

    private String _value;

    TopLevelConstants(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
