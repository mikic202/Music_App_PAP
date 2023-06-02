package client.ServerConnectionConstants;

public enum ChatMessagesConstants {
    USER_ID("user_id"), USERNAME("username");

    private String _value;

    ChatMessagesConstants(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
