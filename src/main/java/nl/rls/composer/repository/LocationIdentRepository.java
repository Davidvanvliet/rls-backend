package nl.rls.composer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.LocationIdent;

public interface LocationIdentRepository extends CrudRepository<LocationIdent, Integer> {
	Optional<LocationIdent> findByLocationPrimaryCode(Integer id);
//	@Query("select li from LocationIdent li where LOWER(li.code) = LOWER(?1)")
	List<LocationIdent> findByCodeIgnoreCase(String code);
	List<LocationIdent> findByPrimaryLocationNameContainingIgnoreCase(String name);
}
