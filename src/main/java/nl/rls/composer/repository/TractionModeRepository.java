package nl.rls.composer.repository;

import nl.rls.composer.domain.code.TractionMode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TractionModeRepository extends CrudRepository<TractionMode, Integer> {
    List<TractionMode> findAll();

    Optional<TractionMode> findByCode(String code);
}