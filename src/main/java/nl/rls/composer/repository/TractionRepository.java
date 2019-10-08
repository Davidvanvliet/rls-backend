package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Traction;

public interface TractionRepository extends CrudRepository<Traction, Integer> {
	Optional<Traction> findByIdAndOwnerId(int id, int ownerId);
	Iterable<Traction> findByOwnerId(int ownerId);
}
