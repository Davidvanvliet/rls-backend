package nl.rls.composer.repository;

import nl.rls.composer.domain.code.TractionType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TractionTypeRepository extends CrudRepository<TractionType, Integer> {
    Optional<TractionType> findByCode(String code);
}
