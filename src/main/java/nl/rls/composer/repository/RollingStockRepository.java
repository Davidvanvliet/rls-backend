package nl.rls.composer.repository;

import nl.rls.composer.domain.RollingStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RollingStockRepository extends JpaRepository<RollingStock, Integer> {
    List<RollingStock> findAllByTrainCompositionIdAndTrainCompositionOwnerId(int trainCompositionId, int ownerId);

    Optional<RollingStock> findByIdAndTrainCompositionIdAndTrainCompositionOwnerId(int rollingStockId, int trainCompositionId, int ownerId);
}
