package nl.rls.composer.repository;

import nl.rls.composer.domain.Wagon;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WagonRepository extends CrudRepository<Wagon, Integer> {
    Iterable<Wagon> findByOwnerId(int ownerId);

    Optional<Wagon> findByOwnerIdAndId(int ownerId, Integer id);

    Optional<Wagon> findByOwnerIdAndNumberFreight(int ownerId, String string);
}
