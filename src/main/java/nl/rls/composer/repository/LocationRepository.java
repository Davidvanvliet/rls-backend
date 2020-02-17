package nl.rls.composer.repository;

import nl.rls.composer.domain.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    Optional<Location> findByLocationPrimaryCode(Integer id);

    //	@Query("select li from LocationIdent li where LOWER(li.code) = LOWER(?1)")
    List<Location> findByCodeIgnoreCaseOrderByCodeAsc(String code);

    List<Location> findByPrimaryLocationNameContainingIgnoreCaseOrderByPrimaryLocationNameAsc(String name);
}
