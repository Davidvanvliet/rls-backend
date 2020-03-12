package nl.rls.composer.controller;

import io.swagger.annotations.Api;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.Company;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.rest.dto.CompanyDto;
import nl.rls.composer.rest.dto.mapper.CompanyDtoMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/companies")
@Api(value = "Access to all companies, RU's and IM's")
public class CompanyController {
    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping(value = "{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyDto> getById(@PathVariable Integer id) {
        Optional<Company> optional = companyRepository.findById(id);
        if (optional.isPresent()) {
            CompanyDto companyDto = CompanyDtoMapper
                    .map(optional.get());
            return ResponseEntity.ok(companyDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompanyDto>> getAll(
            @RequestParam(name = "country", required = false) String countryIso,
            @RequestParam(name = "code", required = false) String code) {
        Iterable<Company> companyList = null;
        if (countryIso != null) {
            companyList = companyRepository.findByCountryIso(countryIso);
        } else if (code != null) {
            companyList = companyRepository.findByCode(code);
        } else if (code == null && countryIso == null) {
            companyList = companyRepository.findAll();
        }

        List<CompanyDto> companyDtoList = new ArrayList<>();

        for (Company company : companyList) {
            companyDtoList.add(CompanyDtoMapper.map(company));
        }
//		Link link = linkTo(methodOn(CompanyController.class).getAll(countryIso, code)).withSelfRel();
//		CollectionModel<CompanyDto> locations = new CollectionModel<CompanyDto>(companyDtoList, link);
        return ResponseEntity.ok(companyDtoList);
    }
}
