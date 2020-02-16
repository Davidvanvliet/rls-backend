package nl.rls.ci.aa.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.ci.aa.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	Optional<Role> findByName(String string);
}
