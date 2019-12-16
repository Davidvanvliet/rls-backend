package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Wagon;

public interface WagonRepository extends CrudRepository<Wagon, Integer> {
	Iterable<Wagon> findByOwnerId(int ownerId);
	Optional<Wagon> findByOwnerIdAndId(int ownerId, Integer id);
	Optional<Wagon> findByOwnerIdAndNumberFreight(int ownerId, String string);
}
