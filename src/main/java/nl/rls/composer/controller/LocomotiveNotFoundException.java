package nl.rls.composer.controller;

@SuppressWarnings("serial")
public class LocomotiveNotFoundException extends RuntimeException {

	public LocomotiveNotFoundException(Integer id) {
        super("Locomotive id not found : " + id);
	}
}
