package DatabaseInteractors;

public enum DatabseInformation {
    PASSWORD(""), URL("jdbc:mysql://localhost:3306/PAP_app"), USER("root");

    private String _value;

    DatabseInformation(String value) {
        _value = value;
    }

    String value() {
        return _value;
    }
}
