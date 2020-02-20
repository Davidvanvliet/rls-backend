package nl.rls.composer.repository;

import nl.rls.composer.domain.LocoTypeNumber;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocoTypeNumberRepository extends CrudRepository<LocoTypeNumber, Integer> {
    Optional<LocoTypeNumber> findByTypeCode1(String typeCode1);
}