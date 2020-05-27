package nl.rls.composer.exceptions;

public class RollingStockNotInCompositionException extends RuntimeException {

    public RollingStockNotInCompositionException(int rollingStockId) {
        super(String.format("Rolling stock %d is not in this composition", rollingStockId));
    }
}
