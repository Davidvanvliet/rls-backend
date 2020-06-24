package nl.rls.composer.exceptions;

public class NewRollingStockPositionOutOfBoundsException extends RuntimeException {
    public NewRollingStockPositionOutOfBoundsException() {
        super("New position is out of bounds");
    }
}
