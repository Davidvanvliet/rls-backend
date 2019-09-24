package nl.rls.composer.controller;

@SuppressWarnings("serial")
public class WagonTechDataNotFoundException extends RuntimeException {
	public WagonTechDataNotFoundException(Integer id) {
        super("WagonTechData id not found : " + id);
	}
}
