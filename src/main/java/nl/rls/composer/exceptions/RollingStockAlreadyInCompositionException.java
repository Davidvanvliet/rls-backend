package nl.rls.composer.exceptions;

public class RollingStockAlreadyInCompositionException extends RuntimeException {

    public RollingStockAlreadyInCompositionException(Long stockIdentifier) {
        super(String.format("Stock %d is already in this composition", stockIdentifier));
    }
}
