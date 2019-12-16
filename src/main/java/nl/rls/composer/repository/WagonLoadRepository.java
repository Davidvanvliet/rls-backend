package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.WagonLoad;

public interface WagonLoadRepository extends CrudRepository<WagonLoad, Integer> {
	Optional<WagonLoad> findByIdAndOwnerId(int id, int ownerId);
	Iterable<WagonLoad> findByOwnerId(int ownerId);
}
