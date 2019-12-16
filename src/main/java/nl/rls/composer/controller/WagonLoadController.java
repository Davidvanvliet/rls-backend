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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.domain.WagonLoad;
import nl.rls.composer.repository.WagonLoadRepository;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.WagonLoadCreateDto;
import nl.rls.composer.rest.dto.WagonLoadDto;
import nl.rls.composer.rest.dto.mapper.WagonLoadDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+"wagonloads")
public class WagonLoadController {
	@Autowired
	private WagonLoadRepository wagonLoadRepository;
	@Autowired
	private WagonRepository wagonRepository;
	@Autowired
	private SecurityContext securityContext;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<WagonLoadDto>> getAll() {
		int ownerId = securityContext.getOwnerId();
		Iterable<WagonLoad> wagonList = wagonLoadRepository.findByOwnerId(ownerId);
		List<WagonLoadDto> dtoList = new ArrayList<>();
		for (WagonLoad wagonLoad : wagonList) {
			WagonLoadDto dto = WagonLoadDtoMapper.map(wagonLoad);
			dtoList.add(dto);
		}
		Link dtoLink = linkTo(methodOn(WagonLoadController.class).getAll()).withSelfRel();
		Resources<WagonLoadDto> wagonLoadDtoList = new Resources<WagonLoadDto>(dtoList, dtoLink);
		return ResponseEntity.ok(wagonLoadDtoList);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonLoadDto> getById(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		WagonLoadDto dto = WagonLoadDtoMapper.map(wagonLoadRepository.findByIdAndOwnerId(id, ownerId).orElseThrow(() -> new WagonNotFoundException(id)));
		return ResponseEntity.ok(dto);
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonLoadDto> create(@RequestBody WagonLoadCreateDto dto) {
		int ownerId = securityContext.getOwnerId();
		WagonLoad entity = new WagonLoad();
		entity.setOwnerId(ownerId);
		int wagonId = DecodePath.decodeInteger(dto.getWagon(), "wagons");
		System.out.println("URL: "+dto.getWagon()+", "+wagonId);
		Optional<Wagon> wagon = wagonRepository.findById(wagonId);
		if (wagon.isPresent()) {
			entity.setWagon(wagon.get());
		}
		wagonLoadRepository.save(entity);
		System.out.println("WagonLoad: "+entity);
		WagonLoadDto wagonLoadDto = WagonLoadDtoMapper.map(entity);
		return ResponseEntity.created(linkTo(methodOn(WagonController.class).getById(entity.getId()))
				.toUri()).body(wagonLoadDto);
	}

}
