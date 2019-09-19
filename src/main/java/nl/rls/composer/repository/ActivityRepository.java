package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Integer>  {

}
