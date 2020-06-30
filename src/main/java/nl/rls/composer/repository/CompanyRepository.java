package nl.rls.composer.repository;

import nl.rls.composer.domain.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
    List<Company> findAll();

    Optional<Company> findByCode(String code);

    List<Company> findByCountryIso(String countryIso);
}