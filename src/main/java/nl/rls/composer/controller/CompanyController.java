package nl.rls.composer.controller;

import io.swagger.annotations.Api;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.Company;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.rest.dto.CompanyDto;
import nl.rls.composer.rest.dto.mapper.CompanyDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/companies")
@Api(value = "Access to all companies, RU's and IM's")
public class CompanyController {
    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping(value = "/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<CompanyDto> getById(@PathVariable int companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find company with id %d", companyId)));
        CompanyDto companyDto = CompanyDtoMapper.map(company);
        return ResponseBuilder.ok()
                .data(companyDto)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<CompanyDto>> getAll(
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
        return ResponseBuilder.ok()
                .data(companyDtoList)
                .build();
    }
}
