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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.composer.domain.code.TractionMode;
import nl.rls.composer.repository.TractionModeRepository;
import nl.rls.composer.rest.dto.TractionModeDto;
import nl.rls.composer.rest.dto.mapper.TractionModeDtoMapper;

@RestController
@RequestMapping("/api/v1/tractionmodes")
public class TractionModeController {
	@Autowired
	private TractionModeRepository tractionModeRepository;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TractionModeDto> getTractionMode(@PathVariable Integer id) {
		Optional<TractionMode> optional = tractionModeRepository.findById(id);
		if (optional.isPresent()) {
			TractionModeDto tractionModeDto = TractionModeDtoMapper
					.map(optional.get());
			return ResponseEntity.ok(tractionModeDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TractionModeDto> getTractionModeByCode(@RequestParam("code") String code) {
		Optional<TractionMode> optional = tractionModeRepository.findByCode(code);
		if (optional.isPresent()) {
			TractionModeDto tractionModeDto = TractionModeDtoMapper
					.map(optional.get());
			return ResponseEntity.ok(tractionModeDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<TractionModeDto>> getAll() {
		Iterable<TractionMode> tractionModeList = tractionModeRepository.findAll();
		List<TractionModeDto> tractionModeDtoList = new ArrayList<>();

		for (TractionMode tractionMode : tractionModeList) {
			tractionModeDtoList.add(TractionModeDtoMapper.map(tractionMode));
		}
		Link tractionModesLink = linkTo(methodOn(TractionModeController.class).getAll()).withSelfRel();
		Resources<TractionModeDto> tractionModes = new Resources<TractionModeDto>(tractionModeDtoList, tractionModesLink);
		return ResponseEntity.ok(tractionModes);
	}

}
