package Chat;

public enum RequestTypes {
    GET_MESSAGES("messages"), GET_USERS_CONVERSATIONS("user conversations");

    private String _value;

    RequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
