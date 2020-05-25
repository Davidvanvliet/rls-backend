package nl.rls.composer.domain.code;

public enum StockType {
    TRACTION("traction"),
    WAGON("wagon");

    private final String typeName;

    StockType(String typeName) {
        this.typeName = typeName;
    }

    public String type() {
        return typeName;
    }

    @Override
    public String toString() {
        return "StockType{" +
                "typeName='" + typeName + '\'' +
                '}';
    }
}
