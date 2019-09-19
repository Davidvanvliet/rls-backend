package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.code.TractionType;

public interface TractionTypeRepository extends CrudRepository<TractionType, Integer> {
	Optional<TractionType> findByCode(String code);
}
