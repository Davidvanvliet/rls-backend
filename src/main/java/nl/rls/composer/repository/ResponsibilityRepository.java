package nl.rls.composer.repository;

import nl.rls.composer.domain.Responsibility;
import org.springframework.data.repository.CrudRepository;

public interface ResponsibilityRepository extends CrudRepository<Responsibility, Integer> {

    Responsibility findByOwnerId(int ownerId);

}
