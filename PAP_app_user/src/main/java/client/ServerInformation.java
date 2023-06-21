package client;

public enum ServerInformation {
    SERVER_IP("144.91.114.89");

    private String _value;

    ServerInformation(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
