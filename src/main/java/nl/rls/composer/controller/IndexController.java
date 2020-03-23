package nl.rls.composer.controller;

import nl.rls.composer.rest.dto.IndexDto;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//public class IndexController {
//
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public Response<IndexDto> getIndex() {
//        IndexDto indexDto = new IndexDto();
//        indexDto.setName("RailLinkSystem REST api is running.");
//        indexDto.add(linkTo(methodOn(TrainCompositionMessageController.class).getAll()).withRel("tcm"));
//        indexDto.add(linkTo(methodOn(CompanyController.class).getAll("", "")).withRel("companies"));
//        indexDto.add(linkTo(methodOn(DangerLabelController.class).getAll()).withRel("dangerlabels"));
//        indexDto.add(linkTo(methodOn(LocationController.class).getAllQuery("", "")).withRel("locationidents"));
//        indexDto.add(linkTo(methodOn(RestrictionCodeController.class).getAll("")).withRel("restrictioncodes"));
//        indexDto.add(linkTo(methodOn(TractionModeController.class).getAll("")).withRel("tractionmodes"));
//        indexDto.add(linkTo(methodOn(TrainActivityTypeController.class).getAll("")).withRel("trainactivitycodes"));
//        indexDto.add(linkTo(methodOn(WagonController.class).getAll()).withRel("wagons"));
//        return ResponseBuilder.ok()
//                .data(indexDto)
//                .build();
//    }
//
//    @GetMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public Response<IndexDto> getTest() {
//        IndexDto indexDto = new IndexDto();
//        indexDto.setName("RailLinkSystem REST api is running.");
//        indexDto.add(linkTo(methodOn(TrainCompositionMessageController.class).getAll()).withRel("tcm"));
//
//        return ResponseBuilder.ok()
//                .data(indexDto)
//                .build();
//    }
//
//}
