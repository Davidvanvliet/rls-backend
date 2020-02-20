package nl.rls.composer.repository;

import nl.rls.composer.domain.code.TractionMode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TractionModeRepository extends CrudRepository<TractionMode, Integer> {
    Optional<TractionMode> findByCode(String code);
}