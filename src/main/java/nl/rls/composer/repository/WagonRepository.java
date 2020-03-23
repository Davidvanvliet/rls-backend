package nl.rls.composer.repository;

import nl.rls.composer.domain.Wagon;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WagonRepository extends CrudRepository<Wagon, Integer> {
    List<Wagon> findByOwnerId(int ownerId);

    Optional<Wagon> findByIdAndOwnerId(int id, int ownerId);

    Optional<Wagon> findByNumberFreightAndOwnerId(String numberFreight, int ownerId);
}
