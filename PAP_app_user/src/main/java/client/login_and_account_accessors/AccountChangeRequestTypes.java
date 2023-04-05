package client.login_and_account_accessors;

public enum AccountChangeRequestTypes {
    SEND_DATA("send data"),
    SEND_AVATAR("send avatar");

    private String _value;

    AccountChangeRequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
