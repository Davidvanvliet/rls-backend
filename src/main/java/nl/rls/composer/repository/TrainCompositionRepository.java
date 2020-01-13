package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.TrainComposition;

public interface TrainCompositionRepository extends CrudRepository<TrainComposition, Integer>  {

	Optional<TrainComposition> findByIdAndOwnerId(Integer id, int ownerId);

}
