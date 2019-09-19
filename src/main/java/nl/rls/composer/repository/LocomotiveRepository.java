package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Locomotive;

public interface LocomotiveRepository extends CrudRepository<Locomotive, Integer> {
	Optional<Locomotive> findByIdAndOwnerId(int id, int ownerId);
	Iterable<Locomotive> findByOwnerId(int ownerId);
}
