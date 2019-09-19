package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.LocoTypeNumber;

public interface LocoTypeNumberRepository extends CrudRepository<LocoTypeNumber, Integer> {
	Optional<LocoTypeNumber> findByTypeCode1(String typeCode1);
}