package client.GUI;

public enum LoginRequestTypes {
    SEND_LOGIN("send login"),
    SEND_REGISTER("send request");

    private String _value;

    LoginRequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
