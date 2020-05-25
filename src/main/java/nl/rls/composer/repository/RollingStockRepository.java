package nl.rls.composer.repository;

import nl.rls.composer.domain.RollingStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RollingStockRepository extends JpaRepository<RollingStock, Integer> {
    List<RollingStock> findAllByTrainCompositionIdAndTrainCompositionOwnerId(Integer trainCompositionId, Integer ownerId);
}
