package nl.rls.composer.repository;

import nl.rls.composer.domain.Responsibility;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ResponsibilityRepository extends CrudRepository<Responsibility, Integer> {

    Optional<Responsibility> findByOwnerId(int ownerId);

}
