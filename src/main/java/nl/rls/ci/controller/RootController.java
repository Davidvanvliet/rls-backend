package nl.rls.ci.controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class RootController {
    @GetMapping("/api/v1")
    ResponseEntity<RepresentationModel> root() {
        RepresentationModel resourceSupport = new RepresentationModel();
        resourceSupport.add(linkTo(methodOn(RootController.class).root()).withSelfRel());
        return ResponseEntity.ok(resourceSupport);
    }

}
