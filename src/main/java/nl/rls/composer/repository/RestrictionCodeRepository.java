package nl.rls.composer.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.code.RestrictionCode;

public interface RestrictionCodeRepository extends CrudRepository<RestrictionCode, Integer> {
	Optional<RestrictionCode> findByCode(String code);
}