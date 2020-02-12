package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.WagonDto;
import nl.rls.composer.rest.dto.WagonPostDto;
import nl.rls.composer.rest.dto.mapper.WagonDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+"wagons")
public class WagonController {
	@Autowired
	private WagonRepository wagonRepository;

	@Autowired
	private SecurityContext securityContext;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WagonDto>> getAll() {
		int ownerId = securityContext.getOwnerId();
		Iterable<Wagon> wagonList = wagonRepository.findByOwnerId(ownerId);
		List<WagonDto> wagonDtoList = new ArrayList<>();
		for (Wagon wagon : wagonList) {
			WagonDto wagonDto = WagonDtoMapper.map(wagon);
			wagonDtoList.add(wagonDto);
		}
//		Link wagonLink = linkTo(methodOn(WagonController.class).getAll()).withSelfRel();
//		Resources<WagonDto> locations = new Resources<WagonDto>(wagonDtoList, wagonLink);
		return ResponseEntity.ok(wagonDtoList);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonDto> getById(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		Optional<Wagon> entity = wagonRepository.findByOwnerIdAndId(ownerId, id);
		if (entity.isPresent()) {
			WagonDto dto = WagonDtoMapper.map(entity.get());
			return ResponseEntity.ok(dto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonDto> create(@RequestBody WagonPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		Wagon entity = new Wagon();
		entity.setOwnerId(ownerId);
		entity.setNumberFreight(dto.getNumberFreight());
		entity.setCode(dto.getCode());
		entity.setHandBrakeBrakedWeight(dto.getHandBrakeBrakedWeight());
		entity.setLengthOverBuffers(dto.getLengthOverBuffers());
		entity.setName(dto.getName());
		entity.setWagonNumberOfAxles(dto.getWagonNumberOfAxles());
		entity.setWagonWeightEmpty(dto.getWagonWeightEmpty());
		wagonRepository.save(entity);
		WagonDto wagonDto = WagonDtoMapper.map(entity);
		return ResponseEntity.created(linkTo(methodOn(WagonController.class).getById(entity.getId()))
				.toUri()).body(wagonDto);
	}

}
