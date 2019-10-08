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
import nl.rls.composer.domain.WagonIdent;
import nl.rls.composer.repository.WagonIdentRepository;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.WagonCreateDto;
import nl.rls.composer.rest.dto.WagonDto;
import nl.rls.composer.rest.dto.mapper.WagonDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+"wagons")
public class WagonController {
	@Autowired
	private WagonRepository wagonRepository;
	@Autowired
	private WagonIdentRepository wagonIdentRepository;
	@Autowired
	private SecurityContext securityContext;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<WagonDto>> getAll() {
		int ownerId = securityContext.getOwnerId();
		Iterable<Wagon> wagonList = wagonRepository.findByOwnerId(ownerId);
		List<WagonDto> dtoList = new ArrayList<>();
		for (Wagon wagon : wagonList) {
			WagonDto dto = WagonDtoMapper.map(wagon);
			dtoList.add(dto);
		}
		Link dtoLink = linkTo(methodOn(WagonController.class).getAll()).withSelfRel();
		Resources<WagonDto> wagonDtoList = new Resources<WagonDto>(dtoList, dtoLink);
		return ResponseEntity.ok(wagonDtoList);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonDto> getById(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		WagonDto dto = WagonDtoMapper.map(wagonRepository.findByIdAndOwnerId(id, ownerId).orElseThrow(() -> new WagonNotFoundException(id)));
		return ResponseEntity.ok(dto);
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonDto> create(@RequestBody WagonCreateDto dto) {
		int ownerId = securityContext.getOwnerId();
		Wagon entity = new Wagon();
		entity.setOwnerId(ownerId);
		int wagonIdentId = DecodePath.decodeInteger(dto.getWagonIdent(), "wagonidents");
		System.out.println("URL: "+dto.getWagonIdent()+", "+wagonIdentId);
		Optional<WagonIdent> wagonIdent = wagonIdentRepository.findById(wagonIdentId);
		if (wagonIdent.isPresent()) {
			entity.setWagonNumberFreight(wagonIdent.get());
		}
		wagonRepository.save(entity);
		System.out.println("Wagon: "+entity);
		WagonDto wagonDto = WagonDtoMapper.map(entity);
		return ResponseEntity.created(linkTo(methodOn(WagonController.class).getById(entity.getId()))
				.toUri()).body(wagonDto);
	}

}
