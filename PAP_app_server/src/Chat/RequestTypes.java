package Chat;

public enum RequestTypes {
    GET_MESSAGES("messages");

    private String _value;

    RequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
