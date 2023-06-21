package client;

public enum ServerInformation {
    SERVER_IP("144.91.114.89"), MAIN_PORT(8000), FILE_PORT(8001), MESSAGE_UPDATER_PORT(8005);

    private String _value;
    private int _intValue;

    ServerInformation(String value) {
        _value = value;
    }

    ServerInformation(int value) {
        _intValue = value;
    }

    public String value() {
        return _value;
    }

    public int intValue() {
        return _intValue;
    }
}
