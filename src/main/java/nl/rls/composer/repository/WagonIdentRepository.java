package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.WagonIdent;

public interface WagonIdentRepository extends CrudRepository<WagonIdent, Integer> {
	Optional<WagonIdent> findByWagonNumberFreight(String number);
}
