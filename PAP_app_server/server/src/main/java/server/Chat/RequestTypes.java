package server.Chat;

public enum RequestTypes {
    GET_MESSAGES("messages"),
    GET_USERS_CONVERSATIONS("user conversations"),
    SEND_MESSAGE("send message"),
    CREATE_CONVERSATION("create_conversation"),
    addUserToConversation("add user to cconversation"),
    USER_INFO("user information"),
    getUsersInConversation("get users in conversation"),
    getLatestMessage("get latest message");

    private String _value;

    RequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
