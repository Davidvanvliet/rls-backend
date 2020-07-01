package nl.rls.composer.domain.code;

/**
 * @author berend.wilkens
 * Code List
 */
public enum CountryISO {
    BELGIUM("BE"),
    DENMARK("DK"),
    FRANCE("FR"),
    GERMANY("DE"),
    ITALY("IT"),
    NETHERLANDS("NL");

    private final String countryISO;

    CountryISO(String countryISO) {
        this.countryISO = countryISO;
    }

    public String code() {
        return countryISO;
    }
}
