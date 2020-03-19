package nl.rls.composer.repository;


import nl.rls.composer.domain.code.RestrictionCode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestrictionCodeRepository extends CrudRepository<RestrictionCode, Integer> {
    Optional<RestrictionCode> findByCode(String code);

    List<RestrictionCode> findAll();
}