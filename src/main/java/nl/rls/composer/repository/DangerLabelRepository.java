package nl.rls.composer.repository;

import nl.rls.composer.domain.code.DangerLabel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DangerLabelRepository extends CrudRepository<DangerLabel, Integer> {
    Optional<DangerLabel> findByCode(String code);

    List<DangerLabel> findAll();
}
