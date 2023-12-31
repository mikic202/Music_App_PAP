package client.Music;

public enum MusicRequestTypes {
    START_STREAM("start_stream"),
    CHECK_IF_PLAYING("check_if_playing"),
    JOIN_PLAYING_STREAM("join_playing_stream"),
    RESUME_STREAM("resume_stream"),
    PAUSE_STREAM("pause_stream"),
    LEAVE_STREAM("leave_stream"),
    GET_USER_SONGS("user_songs");

    private String _value;

    MusicRequestTypes(String value) {
        _value = value;
    }

    public String value() {
        return _value;
    }
}
