package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Company;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
	Optional<Company> findByCode(String code);
}