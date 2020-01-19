package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.DangerGoodsType;

public interface DangerGoodsTypeRepository extends CrudRepository<DangerGoodsType, Integer> {
}