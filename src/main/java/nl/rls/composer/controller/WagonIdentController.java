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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.WagonIdent;
import nl.rls.composer.domain.WagonTechData;
import nl.rls.composer.repository.WagonIdentRepository;
import nl.rls.composer.repository.WagonTechDataRepository;
import nl.rls.composer.rest.dto.WagonIdentDto;
import nl.rls.composer.rest.dto.WagonIdentPostDto;
import nl.rls.composer.rest.dto.mapper.WagonIdentDtoMapper;

@RestController
@RequestMapping("/api/v1/wagonidents")
public class WagonIdentController {
	@Autowired
	private WagonIdentRepository wagonIdentRepository;
	@Autowired
	private WagonTechDataRepository wagonTechDataRepository;

	@Autowired
	private SecurityContext securityContext;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<WagonIdentDto>> getAll() {
		int ownerId = securityContext.getOwnerId();
		Iterable<WagonIdent> wagonIdentList = wagonIdentRepository.findByOwnerId(ownerId);
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
	public ResponseEntity<WagonIdentDto> getById(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		Optional<WagonIdent> entity = wagonIdentRepository.findByOwnerIdAndId(ownerId, id);
		if (entity.isPresent()) {
			WagonIdentDto dto = WagonIdentDtoMapper.map(entity.get());
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonIdentDto> create(@RequestBody WagonIdentPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		WagonIdent entity = new WagonIdent();
		entity.setOwnerId(ownerId);
		entity.setWagonNumberFreight(dto.getWagonNumberFreight());
		int locationId = DecodePath.decodeInteger(dto.getWagonTechData(), "wagontechdata");
		System.out.println("URL: "+dto.getWagonTechData()+", "+locationId);
		Optional<WagonTechData> wagonTechData = wagonTechDataRepository.findById(locationId);
		if (wagonTechData.isPresent()) {
			entity.setWagonTechData(wagonTechData.get());
		}
		wagonIdentRepository.save(entity);
		WagonIdentDto wagonIdentDto = WagonIdentDtoMapper.map(entity);
		return ResponseEntity.created(linkTo(methodOn(WagonIdentController.class).getById(entity.getId()))
				.toUri()).body(wagonIdentDto);
	}

}
