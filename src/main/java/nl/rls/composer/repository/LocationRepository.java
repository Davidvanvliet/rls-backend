package nl.rls.composer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Location;

public interface LocationRepository extends CrudRepository<Location, Integer> {
	Optional<Location> findByLocationPrimaryCode(Integer id);
//	@Query("select li from LocationIdent li where LOWER(li.code) = LOWER(?1)")
	List<Location> findByCodeIgnoreCaseOrderByCodeAsc(String code);
	List<Location> findByPrimaryLocationNameContainingIgnoreCaseOrderByPrimaryLocationNameAsc(String name);
}
