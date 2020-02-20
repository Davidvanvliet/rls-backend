package nl.rls.ci.controller;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RootController {
    @GetMapping("/api/v1")
    ResponseEntity<ResourceSupport> root() {
        ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(linkTo(methodOn(RootController.class).root()).withSelfRel());
        resourceSupport.add(linkTo(methodOn(CiController.class).getAll()).withRel("ci"));
        return ResponseEntity.ok(resourceSupport);
    }

}