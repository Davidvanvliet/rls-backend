package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.code.TractionMode;

public interface TractionModeRepository extends CrudRepository<TractionMode, Integer> {
	Optional<TractionMode> findByCode(String code);
}