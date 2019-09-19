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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.composer.domain.WagonIdent;
import nl.rls.composer.repository.WagonIdentRepository;
import nl.rls.composer.rest.dto.WagonIdentDto;
import nl.rls.composer.rest.dto.mapper.WagonIdentDtoMapper;

@RestController
@RequestMapping("/api/v1/wagonidents")
public class WagonIdentController {
	@Autowired
	private WagonIdentRepository wagonIdentRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<WagonIdentDto>> getAll() {
		Iterable<WagonIdent> wagonIdentList = wagonIdentRepository.findAll();
		List<WagonIdentDto> wagonIdentDtoList = new ArrayList<>();
		for (WagonIdent wagonIdent : wagonIdentList) {
			WagonIdentDto wagonIdentDto = WagonIdentDtoMapper.map(wagonIdent);
			wagonIdentDtoList.add(wagonIdentDto);
		}

		Link wagonIdentLink = linkTo(methodOn(WagonIdentController.class).getAll()).withSelfRel();
		Resources<WagonIdentDto> locations = new Resources<WagonIdentDto>(wagonIdentDtoList, wagonIdentLink);
		return ResponseEntity.ok(locations);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public WagonIdentDto getById(@PathVariable Integer id) {
		WagonIdentDto wagonIdentDto = WagonIdentDtoMapper.map(wagonIdentRepository.findById(id)
				.orElseThrow(() -> new WagonIdentNotFoundException(id)));
		return wagonIdentDto;
	}

}
