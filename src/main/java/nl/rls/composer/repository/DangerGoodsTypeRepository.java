package nl.rls.composer.repository;

import nl.rls.composer.domain.DangerGoodsType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DangerGoodsTypeRepository extends CrudRepository<DangerGoodsType, Integer> {
    List<DangerGoodsType> findAll();
}