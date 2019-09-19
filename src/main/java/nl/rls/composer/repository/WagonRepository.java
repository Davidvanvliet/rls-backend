package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Wagon;

public interface WagonRepository extends CrudRepository<Wagon, Integer> {
	Optional<Wagon> findByIdAndOwnerId(int id, int ownerId);
	Iterable<Wagon> findByOwnerId(int ownerId);
}
