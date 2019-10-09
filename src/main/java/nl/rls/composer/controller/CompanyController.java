package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import nl.rls.composer.domain.Company;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.rest.dto.CompanyDto;
import nl.rls.composer.rest.dto.mapper.CompanyDtoMapper;

@Api(value = "Access to all companies, RU's and IM's")
@RestController
@RequestMapping("/api/v1/companies/")
public class CompanyController {
	@Autowired
	private CompanyRepository companyRepository;

	@GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
	public ResponseEntity<Resources<CompanyDto>> getAll(
		@RequestParam(name = "country", required = false) String countryIso, 
		@RequestParam(name= "code", required = false) String code) {
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
		Link link = linkTo(methodOn(CompanyController.class).getAll(countryIso, code)).withSelfRel();
		Resources<CompanyDto> locations = new Resources<CompanyDto>(companyDtoList, link);
		return ResponseEntity.ok(locations);
	}
}
