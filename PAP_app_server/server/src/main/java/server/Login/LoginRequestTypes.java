package server.Login;

public enum LoginRequestTypes {
    SEND_LOGIN("send login"),
    SEND_REGISTER("send request"),
    SEND_CHANGE_PASSWORD("send change password"),
    SEND_CHANGE_EMAIL("send email"),
    SEND_CHANGE_NICKNAME("send nickname");

    private String _value;

    LoginRequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
