package client.login_and_account_accessors;

public enum AccountChangeRequestTypes {
    SEND_DATA("send data"),
    SEND_AVATAR("send avatar"),
    SEND_EMAIL("send email"),
    SEND_NICKNAME("send nickname"),
    SEND_NEW_PASSWORD("send new password"),
    SEND_PASSWORD("send password"),
    SEND_CONFIRM_NEW_PASSWORD("send confirm new password");

    private String _value;

    AccountChangeRequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
