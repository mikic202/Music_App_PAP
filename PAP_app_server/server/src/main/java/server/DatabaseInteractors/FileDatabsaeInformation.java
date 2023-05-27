package server.DatabaseInteractors;

public enum FileDatabsaeInformation {
    FILE_TABLE("files"), USER_FILE_RELATION_TABLE("user_file_relation"),
    ID_COLUMN("file_id"), NAME_COLUMN("file_name"),
    USER_COLUMN("user_id"), FILEPATH_COLUMN("file_path");

    private String _value;

    FileDatabsaeInformation(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
