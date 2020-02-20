package nl.rls.ci.aa.repository;

import nl.rls.ci.aa.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String string);
}
