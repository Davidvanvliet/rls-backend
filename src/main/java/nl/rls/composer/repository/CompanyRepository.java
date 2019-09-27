package nl.rls.composer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Company;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
	List<Company> findByCode(String code);
	List<Company> findByCountryIso(String countryIso);
}