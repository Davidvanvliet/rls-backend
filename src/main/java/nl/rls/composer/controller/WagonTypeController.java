package nl.rls.composer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.WagonType;
import nl.rls.composer.repository.WagonTypeRepository;
import nl.rls.composer.rest.dto.WagonTypeDto;
import nl.rls.composer.rest.dto.mapper.WagonTypeDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+"wagontypes")
public class WagonTypeController {
	@Autowired
	private WagonTypeRepository wagonTechDataRepository;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WagonTypeDto>> getAll() {
		Iterable<WagonType> entityList = wagonTechDataRepository.findAll();
		List<WagonTypeDto> dtoList = new ArrayList<>();
		for (WagonType entity : entityList) {
			WagonTypeDto dto = WagonTypeDtoMapper.map(entity);
			dtoList.add(dto);
		}
//		Link selfLink = linkTo(methodOn(WagonTechDataController.class).getAll()).withSelfRel();
//		Resources<WagonTechDataDto> dtos = new Resources<WagonTechDataDto>(dtoList, selfLink);
		return ResponseEntity.ok(dtoList);
	}


	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonTypeDto> getById(@PathVariable Integer id) {
		WagonTypeDto dto = WagonTypeDtoMapper.map(wagonTechDataRepository.findById(id)
				.orElseThrow(() -> new WagonTechDataNotFoundException(id)));
		return ResponseEntity.ok(dto);
	}
}
