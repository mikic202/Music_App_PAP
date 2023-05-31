package client.Chat;

public enum RequestTypes {
    GET_MESSAGES("messages"), GET_USERS_CONVERSATIONS("user conversations"), SEND_MESSAGE("send message"),
    CREATE_CONVERSATION("create_conversation"), ADD_USER_TO_CONVERSATION("add user to cconversation"),
    USER_INFO("user information"), GET_USERS_IN_CONVERSATION("get users in conversation"),
    GET_LATEST_MESSAGE("get latest message"), SEND_IMAGE("send image"), GET_CONVERSATION_CODE("get conversation code"),
    JOIN_CONVERSATION_WITH_CODE("join using code"), CHANGE_CONVERSATION_NAME("change conversation name");

    private String _value;

    RequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
