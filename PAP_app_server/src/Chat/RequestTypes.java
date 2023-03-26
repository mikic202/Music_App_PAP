package Chat;

public enum RequestTypes {
    GET_MESSAGES("messages"), GET_USERS_CONVERSATIONS("user conversations"), SEND_MESSAGE("send message"),
    CREATE_CONVERSATION("create_conversation");

    private String _value;

    RequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
