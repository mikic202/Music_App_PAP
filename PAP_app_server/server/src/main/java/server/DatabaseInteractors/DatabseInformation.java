package server.DatabaseInteractors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum DatabseInformation {
    PASSWORD(Paths.get("./config/db_passwd")), URL("jdbc:mysql://localhost:3306/pap"),
    USER(Paths.get("./config/db_user")), USER_TABLE("users"),
    MESSAGES_TABLE("messages"), CONVERSATION_TABLE("conversations"), FILES_TABLE("files"),
    USER_CONVERSATION_TABLE("user_conversation_relation");

    private String _value;

    DatabseInformation(String value) {
        _value = value;
    }

    DatabseInformation(Path path) {
        try {
            _value = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.out.println("Can't load config file: " + path.toString());
        }
    }

    public String value() {
        return _value;
    }
}
