package nl.rls.composer.controller;

@SuppressWarnings("serial")
public class WagonIdentNotFoundException extends RuntimeException {

	public WagonIdentNotFoundException(Integer id) {
        super("WagonTechData id not found : " + id);
	}
}
