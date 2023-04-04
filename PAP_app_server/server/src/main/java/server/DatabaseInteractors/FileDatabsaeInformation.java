package DatabaseInteractors;

public enum FileDatabsaeInformation {
    FILE_TABLE("files"),
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
