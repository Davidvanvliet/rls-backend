package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.code.DangerLabel;

public interface DangerLabelRepository extends CrudRepository<DangerLabel, Integer>{
	Optional<DangerLabel> findByCode(String code);


}
