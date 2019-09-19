package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.LocationIdent;

public interface LocationIdentRepository extends CrudRepository<LocationIdent, Integer> {
	Optional<LocationIdent> findByLocationPrimaryCode(Integer id);

	@Query("select li from LocationIdent li where LOWER(li.code) = LOWER(?1)")
	Optional<LocationIdent> findByCode(String code);
}
