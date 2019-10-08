package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.WagonTechData;
import nl.rls.composer.repository.WagonTechDataRepository;
import nl.rls.composer.rest.dto.WagonTechDataDto;
import nl.rls.composer.rest.dto.mapper.WagonTechDataDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+"wagontechdata")
public class WagonTechDataController {
	@Autowired
	private WagonTechDataRepository wagonTechDataRepository;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<WagonTechDataDto>> getAll() {
		Iterable<WagonTechData> entityList = wagonTechDataRepository.findAll();
		List<WagonTechDataDto> dtoList = new ArrayList<>();
		for (WagonTechData entity : entityList) {
			WagonTechDataDto dto = WagonTechDataDtoMapper.map(entity);
			dtoList.add(dto);
		}
		Link selfLink = linkTo(methodOn(WagonTechDataController.class).getAll()).withSelfRel();
		Resources<WagonTechDataDto> dtos = new Resources<WagonTechDataDto>(dtoList, selfLink);
		return ResponseEntity.ok(dtos);
	}


	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonTechDataDto> getById(@PathVariable Integer id) {
		WagonTechDataDto dto = WagonTechDataDtoMapper.map(wagonTechDataRepository.findById(id)
				.orElseThrow(() -> new WagonTechDataNotFoundException(id)));
		return ResponseEntity.ok(dto);
	}
}
