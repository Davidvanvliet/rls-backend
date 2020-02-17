package nl.rls.composer.repository;

import nl.rls.composer.domain.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Integer> {
    List<Company> findByCode(String code);

    List<Company> findByCountryIso(String countryIso);
}