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
		return ResponseEntity.ok(indexDto);
	}
	
	@GetMapping(path="test", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IndexDto> getTest() {
		IndexDto indexDto = new IndexDto();
		indexDto.setName("RailLinkSystem REST api is running.");
		indexDto.add(linkTo(methodOn(TrainCompositionMessageController.class).getAll()).withRel("tcm"));

		return ResponseEntity.ok(indexDto);
	}

}
