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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Traction;
import nl.rls.composer.domain.code.TractionMode;
import nl.rls.composer.domain.code.TractionType;
import nl.rls.composer.repository.TractionRepository;
import nl.rls.composer.repository.TractionModeRepository;
import nl.rls.composer.repository.TractionTypeRepository;
import nl.rls.composer.rest.dto.TractionCreateDto;
import nl.rls.composer.rest.dto.TractionDto;
import nl.rls.composer.rest.dto.mapper.TractionDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+"tractions")
public class TractionController {
	@Autowired
	private TractionRepository tractionRepository;
	@Autowired
	private TractionTypeRepository tractionTypeRepository;
	@Autowired
	private TractionModeRepository tractionModeRepository;
	@Autowired
	private SecurityContext securityContext;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<TractionDto>> getAll() {
		int ownerId = securityContext.getOwnerId();
		Iterable<Traction> tractionList = tractionRepository.findByOwnerId(ownerId);
		List<TractionDto> dtoList = new ArrayList<>();
		for (Traction traction : tractionList) {
			TractionDto dto = TractionDtoMapper.map(traction);
			dtoList.add(dto);
		}
		Link dtoLink = linkTo(methodOn(this.getClass()).getAll()).withSelfRel();
		Resources<TractionDto> tractionDtoList = new Resources<TractionDto>(dtoList, dtoLink);
		return ResponseEntity.ok(tractionDtoList);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<TractionDto> getById(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		TractionDto dto = TractionDtoMapper
				.map(tractionRepository.findByIdAndOwnerId(id,ownerId).orElseThrow(() -> new TractionNotFoundException(id)));
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TractionDto> create(@RequestBody TractionCreateDto dto) {
		int ownerId = securityContext.getOwnerId();
		Traction entity = TractionDtoMapper.map(dto);
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
		tractionRepository.save(entity);
		System.out.println("Traction: "+entity);
		TractionDto resultDto = TractionDtoMapper.map(entity);
		return ResponseEntity.created(linkTo(methodOn(TractionController.class).getById(entity.getId()))
				.toUri()).body(resultDto);
	}


}
