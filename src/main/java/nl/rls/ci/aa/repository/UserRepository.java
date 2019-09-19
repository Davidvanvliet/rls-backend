package nl.rls.ci.aa.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.ci.aa.domain.AppUser;

public interface UserRepository extends CrudRepository<AppUser, Integer> {
	AppUser findByUsername(String username);
}
