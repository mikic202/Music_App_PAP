package server.Login;

public enum LoginRequestTypes {
    GET_EXISTANCE("existance"), GET_EMAIL("email"), GET_NICKNAME("nickname");

    private String _value;

    LoginRequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
