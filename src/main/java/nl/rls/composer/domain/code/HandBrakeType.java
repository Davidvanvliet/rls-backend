package nl.rls.composer.domain.code;

/**
 * @author berend.wilkens
 * <p>
 * 0 No hand brake
 * 1 Ground-operated hand brake
 * 2 Platform-operated hand brake
 */
public enum HandBrakeType {
    NO_HB("0"), GOH("1"), POH("2");

    private String code;

    HandBrakeType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

}
