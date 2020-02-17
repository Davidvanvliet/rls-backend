package nl.rls.composer.domain.code;

public enum AirBrakeType {
    G("1"),
    P("2"),
    GP("3"),
    NO_GP("8"),
    NON_CODED("9");

    private String code;

    AirBrakeType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }


}
