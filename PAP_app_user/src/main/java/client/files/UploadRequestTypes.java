package client.files;

public enum UploadRequestTypes {
    START_UPLOAD("start upload"),
    FINISH_UPLOAD("finish upload");

    private String _value;

    UploadRequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}