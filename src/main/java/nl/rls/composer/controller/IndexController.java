package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.composer.rest.dto.IndexDto;

@RestController
@RequestMapping("/")
public class IndexController {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IndexDto> getIndex() {
		IndexDto indexDto = new IndexDto();
		indexDto.setName("RailLinkSystem REST api is running.");
		indexDto.add(linkTo(methodOn(TrainCompositionMessageController.class).getAll()).withRel("tcm"));
		indexDto.add(linkTo(methodOn(CompanyController.class).getAll("", "")).withRel("companies"));
		indexDto.add(linkTo(methodOn(DangerLabelController.class).getAll()).withRel("dangerlabels"));
		indexDto.add(linkTo(methodOn(LocationIdentController.class).getAll()).withRel("locationidents"));
		indexDto.add(linkTo(methodOn(RestrictionCodeController.class).getAll()).withRel("restrictioncodes"));
		indexDto.add(linkTo(methodOn(TractionModeController.class).getAll()).withRel("tractionmodes"));
		indexDto.add(linkTo(methodOn(TrainActivityTypeController.class).getAll()).withRel("trainactivitycodes"));
		indexDto.add(linkTo(methodOn(WagonTechDataController.class).getAll()).withRel("wagontechdata"));
		return ResponseEntity.ok(indexDto);
	}

	@GetMapping(path = "test", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IndexDto> getTest() {
		IndexDto indexDto = new IndexDto();
		indexDto.setName("RailLinkSystem REST api is running.");
		indexDto.add(linkTo(methodOn(TrainCompositionMessageController.class).getAll()).withRel("tcm"));

		return ResponseEntity.ok(indexDto);
	}

}
