package nl.rls.auth.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException() {
        super("No username provided.");
    }

    public UsernameNotFoundException(String username) {
        super(String.format("Username %s not found.", username));
    }
}
