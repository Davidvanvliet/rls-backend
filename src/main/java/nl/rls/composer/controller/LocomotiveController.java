package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.composer.domain.Locomotive;
import nl.rls.composer.repository.LocomotiveRepository;
import nl.rls.composer.rest.dto.LocomotiveDto;
import nl.rls.composer.rest.dto.mapper.LocomotiveDtoMapper;

@RestController
@RequestMapping("/api/v1/locomotives")
public class LocomotiveController {
	@Autowired
	private LocomotiveRepository locomotiveRepository;
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

}
