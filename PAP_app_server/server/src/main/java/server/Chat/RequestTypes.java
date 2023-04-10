package server.Chat;

public enum RequestTypes {
    GET_MESSAGES("messages"), GET_USERS_CONVERSATIONS("user conversations"), SEND_MESSAGE("send message"),
    CREATE_CONVERSATION("create_conversation"), ADD_USER_TO_CONVERSATION("add user to conversation"),
    USER_INFO("user information"), START_STREAM("start_stream"), STOP_STREAM("stop_stream");

    private String _value;

    RequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
