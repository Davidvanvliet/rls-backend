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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.url.URL;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.LocationIdent;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.rest.dto.JourneySectionDto;
import nl.rls.composer.rest.dto.JourneySectionPostDto;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;

@RestController
@RequestMapping("/api/v1/journeysections")
public class JourneySectionController {
	@Autowired
	private JourneySectionRepository journeySectionRepository;
	@Autowired
	private LocationIdentRepository locationIdentRepository;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JourneySectionDto> getJourneySection(@PathVariable Integer id) {
		Optional<JourneySection> optional = journeySectionRepository.findById(id);
		if (optional.isPresent()) {
			JourneySectionDto journeySectionDto = JourneySectionDtoMapper.map(optional.get());
			return ResponseEntity.ok(journeySectionDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<JourneySectionDto>> getAll() {

		Iterable<JourneySection> journeySectionList = journeySectionRepository.findAll();
		List<JourneySectionDto> journeySectionDtoList = new ArrayList<>();

		for (JourneySection journeySection : journeySectionList) {
			journeySectionDtoList.add(JourneySectionDtoMapper.map(journeySection));
		}
		Link journeySectionsLink = linkTo(methodOn(LocationIdentController.class).getAll()).withSelfRel();
		Resources<JourneySectionDto> locations = new Resources<JourneySectionDto>(journeySectionDtoList,
				journeySectionsLink);
		return ResponseEntity.ok(locations);
	}

	@PutMapping(value = "/{id}/origin")
	public ResponseEntity<JourneySectionDto> setOriginByCode(@PathVariable("id") Integer id, @RequestParam("code") String code) {
		Optional<JourneySection> journeySectionOptional = journeySectionRepository.findById(id);
		if (!journeySectionOptional.isPresent()) {
		
		}
		Optional<LocationIdent> locationIdentOptional = locationIdentRepository.findByCode(code);
		if (!locationIdentOptional.isPresent()) {
			
		}
		JourneySection journeySection = journeySectionOptional.get();
		journeySection.setJourneySectionOrigin(locationIdentOptional.get());
		journeySectionRepository.save(journeySection);
		JourneySectionDto journeySectionDto = JourneySectionDtoMapper.map(journeySection);
		return ResponseEntity.ok(journeySectionDto);
	}

	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JourneySectionDto> create(@RequestBody JourneySectionPostDto dto) {
		JourneySection journeySection = new JourneySection();
		journeySection.setOwnerId(1);
		//journeySectionRepository.save(journeySection);
		URL url = new URL(dto.getJourneySectionOrigin());
		String locationidents = url.getChildDirectory("locationidents");
		System.out.println("locationidents " + locationidents);
		Integer locationIdentId = new Integer(locationidents);
		Optional<LocationIdent> locationIdentOptional = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
		if (locationIdentOptional.isPresent()) {
			journeySection.setJourneySectionOrigin(locationIdentOptional.get());
		}

		url = new URL(dto.getJourneySectionDestination());
		locationidents = url.getChildDirectory("locationidents");
		System.out.println(url +" locationidents " + locationidents);
		locationIdentId = new Integer(locationidents);
		locationIdentOptional = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
		if (locationIdentOptional.isPresent()) {
			journeySection.setJourneySectionDestination(locationIdentOptional.get());
		}

		journeySectionRepository.save(journeySection);
		JourneySectionDto journeySectionDto = JourneySectionDtoMapper.map(journeySection);
	    
		return ResponseEntity.ok(journeySectionDto);
	}

}
