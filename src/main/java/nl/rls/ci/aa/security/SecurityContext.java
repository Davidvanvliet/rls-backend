package nl.rls.ci.aa.security;

import nl.rls.ci.aa.domain.AppUser;
import nl.rls.ci.aa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author berend.wilkens
 * User in each API - call to verify the correct owner of an object.
 */
@Component
public class SecurityContext {
    @Autowired
    private UserRepository userRepository;

    public int getOwnerId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("getOwnerId - username: " + username);
        if (username == null || username.isEmpty()) {
            return 1;
        }
        AppUser user = userRepository.findByUsername(username);
        if (user != null) {
            System.out.println("OwnerId: " + user.getOwner().getId());
            return 1; //user.getOwner().getId();
        } else {
            return 1;
        }
    }

}
