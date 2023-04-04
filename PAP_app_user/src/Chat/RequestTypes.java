package Chat;

public enum RequestTypes {
    GET_MESSAGES("messages"), GET_USERS_CONVERSATIONS("user conversations"), SEND_MESSAGE("send message"),
    CREATE_CONVERSATION("create_conversation"), ADD_USER_TO_CONVERSATION("add user to cconversation"),
    USER_INFO("user information");
    ;

    private String _value;

    RequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
