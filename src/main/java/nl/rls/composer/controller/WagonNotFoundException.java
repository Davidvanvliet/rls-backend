package nl.rls.composer.controller;

@SuppressWarnings("serial")
public class WagonNotFoundException extends RuntimeException {

    public WagonNotFoundException(Integer id) {
        super("Wagon id not found : " + id);
    }
}
