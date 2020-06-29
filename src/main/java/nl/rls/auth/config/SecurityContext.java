package nl.rls.auth.config;

import nl.rls.auth.domain.User;
import nl.rls.auth.exception.UsernameNotFoundException;
import nl.rls.auth.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author berend.wilkens
 * User in each API - call to verify the correct owner of an object.
 */
@Component
public class SecurityContext {
    private final UserRepository userRepository;

    public SecurityContext(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int getOwnerId() {
        return getUserFromContext().getOwner().getId();
    }

    public String getCompanyCode() {
        return getUserFromContext().getOwner().getCompany().getCode();
    }

    private User getUserFromContext() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException();
        }
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
