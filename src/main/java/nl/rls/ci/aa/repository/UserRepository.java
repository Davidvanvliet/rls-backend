package nl.rls.ci.aa.repository;

import nl.rls.ci.aa.domain.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser, Integer> {
    AppUser findByUsername(String username);
}
