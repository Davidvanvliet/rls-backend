package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.WagonIdent;

public interface WagonIdentRepository extends CrudRepository<WagonIdent, Integer> {
	Iterable<WagonIdent> findByOwnerId(int ownerId);
	Optional<WagonIdent> findByOwnerIdAndId(int ownerId, Integer id);
	Optional<WagonIdent> findByOwnerIdAndWagonNumberFreight(int ownerId, String string);
}
