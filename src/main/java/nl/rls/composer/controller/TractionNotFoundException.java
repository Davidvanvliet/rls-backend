package nl.rls.composer.controller;

@SuppressWarnings("serial")
public class TractionNotFoundException extends RuntimeException {

    public TractionNotFoundException(Integer id) {
        super("Traction id not found : " + id);
    }
}
