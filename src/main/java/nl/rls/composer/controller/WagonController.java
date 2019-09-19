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
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.WagonDto;
import nl.rls.composer.rest.dto.mapper.WagonDtoMapper;

@RestController
@RequestMapping("/api/v1/wagons")
public class WagonController {
	@Autowired
	private WagonRepository wagonRepository;
	@Autowired
	private SecurityContext securityContext;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<WagonDto> getById(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		WagonDto dto = WagonDtoMapper.map(wagonRepository.findByIdAndOwnerId(id, ownerId).orElseThrow(() -> new WagonNotFoundException(id)));
		return ResponseEntity.ok(dto);
	}

}
