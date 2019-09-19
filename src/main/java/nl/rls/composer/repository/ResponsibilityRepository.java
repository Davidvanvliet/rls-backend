package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Responsibility;

public interface ResponsibilityRepository extends CrudRepository<Responsibility, Integer> {

	Responsibility findByOwnerId(int ownerId);

}
