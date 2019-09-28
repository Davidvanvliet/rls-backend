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
import nl.rls.composer.domain.Locomotive;
import nl.rls.composer.domain.code.TractionMode;
import nl.rls.composer.domain.code.TractionType;
import nl.rls.composer.repository.LocomotiveRepository;
import nl.rls.composer.repository.TractionModeRepository;
import nl.rls.composer.repository.TractionTypeRepository;
import nl.rls.composer.rest.dto.LocomotiveCreateDto;
import nl.rls.composer.rest.dto.LocomotiveDto;
import nl.rls.composer.rest.dto.mapper.LocomotiveDtoMapper;

@RestController
@RequestMapping("/api/v1/locomotives")
public class LocomotiveController {
	@Autowired
	private LocomotiveRepository locomotiveRepository;
	@Autowired
	private TractionTypeRepository tractionTypeRepository;
	@Autowired
	private TractionModeRepository tractionModeRepository;
	@Autowired
	private SecurityContext securityContext;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Resources<LocomotiveDto>> getAll() {
		int ownerId = securityContext.getOwnerId();
		Iterable<Locomotive> locomotiveList = locomotiveRepository.findByOwnerId(ownerId);
		List<LocomotiveDto> dtoList = new ArrayList<>();
		for (Locomotive locomotive : locomotiveList) {
			LocomotiveDto dto = LocomotiveDtoMapper.map(locomotive);
			dtoList.add(dto);
		}
		Link dtoLink = linkTo(methodOn(this.getClass()).getAll()).withSelfRel();
		Resources<LocomotiveDto> locomotiveDtoList = new Resources<LocomotiveDto>(dtoList, dtoLink);
		return ResponseEntity.ok(locomotiveDtoList);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<LocomotiveDto> getById(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		LocomotiveDto dto = LocomotiveDtoMapper
				.map(locomotiveRepository.findByIdAndOwnerId(id,ownerId).orElseThrow(() -> new LocomotiveNotFoundException(id)));
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LocomotiveDto> create(@RequestBody LocomotiveCreateDto dto) {
		int ownerId = securityContext.getOwnerId();
		Locomotive entity = LocomotiveDtoMapper.map(dto);
		entity.setOwnerId(ownerId);
		
		int tractionTypeId = DecodePath.decodeInteger(dto.getTractionType(), "tractiontypes");
		Optional<TractionType> tractionType = tractionTypeRepository.findById(tractionTypeId);
		if (tractionType.isPresent()) {
			entity.setTractionType(tractionType.get());
		}
		int tractionModeId = DecodePath.decodeInteger(dto.getTractionMode(), "tractionmodes");
		Optional<TractionMode> tractionMode = tractionModeRepository.findById(tractionModeId);
		if (tractionMode.isPresent()) {
			entity.setTractionMode(tractionMode.get());
		}
		locomotiveRepository.save(entity);
		System.out.println("Locomotive: "+entity);
		LocomotiveDto resultDto = LocomotiveDtoMapper.map(entity);
		return ResponseEntity.created(linkTo(methodOn(LocomotiveController.class).getById(entity.getId()))
				.toUri()).body(resultDto);
	}


}
