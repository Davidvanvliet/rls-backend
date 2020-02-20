package nl.rls.ci.aa.repository;

import nl.rls.ci.aa.domain.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    Optional<Owner> findByCode(String code);

}
