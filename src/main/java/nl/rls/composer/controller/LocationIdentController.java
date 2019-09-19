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

import nl.rls.composer.domain.LocationIdent;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.rest.dto.LocationIdentDto;
import nl.rls.composer.rest.dto.mapper.LocationIdentDtoMapper;

@RestController
@RequestMapping("/api/v1/locationidents")
public class LocationIdentController {
	@Autowired
	private LocationIdentRepository locationIdentRepository;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LocationIdentDto> getById(@PathVariable Integer id) {
		Optional<LocationIdent> optional = locationIdentRepository.findByLocationPrimaryCode(id);
		if (optional.isPresent()) {
			LocationIdentDto locationIdentDto = LocationIdentDtoMapper
					.map(optional.get());
			return ResponseEntity.ok(locationIdentDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LocationIdentDto> getByCode(@RequestParam("code") String code) {
		Optional<LocationIdent> optional = locationIdentRepository.findByCode(code);
		if (optional.isPresent()) {
			LocationIdentDto locationIdentDto = LocationIdentDtoMapper
					.map(optional.get());
			return ResponseEntity.ok(locationIdentDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<LocationIdentDto>> getAll() {
	    
		Iterable<LocationIdent> locationList = locationIdentRepository.findAll();
		List<LocationIdentDto> locationDtoList = new ArrayList<>();
		
		for (LocationIdent locationIdent : locationList) {
			locationDtoList.add(LocationIdentDtoMapper.map(locationIdent));
		}
		Link locationsLink = linkTo(methodOn(LocationIdentController.class).getAll()).withSelfRel();
		Resources<LocationIdentDto> locations = new Resources<LocationIdentDto>(locationDtoList, locationsLink);
		return ResponseEntity.ok(locations);
	}

}
