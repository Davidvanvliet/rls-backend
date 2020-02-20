package nl.rls.composer.controller;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.code.RestrictionCode;
import nl.rls.composer.repository.RestrictionCodeRepository;
import nl.rls.composer.rest.dto.RestrictionCodeDto;
import nl.rls.composer.rest.dto.mapper.RestrictionCodeDtoMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/restrictioncodes")
public class RestrictionCodeController {
    private final RestrictionCodeRepository restrictionCodeRepository;

    public RestrictionCodeController(RestrictionCodeRepository restrictionCodeRepository) {
        this.restrictionCodeRepository = restrictionCodeRepository;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestrictionCodeDto> getRestrictionCode(@PathVariable Integer id) {
        Optional<RestrictionCode> optional = restrictionCodeRepository.findById(id);
        if (optional.isPresent()) {
            RestrictionCodeDto restrictionCodeDto = RestrictionCodeDtoMapper
                    .map(optional.get());
            return ResponseEntity.ok(restrictionCodeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestrictionCodeDto>> getAll(@RequestParam(value = "code", required = false) String code) {
        if (code == null) {
            Iterable<RestrictionCode> restrictionCodeList = restrictionCodeRepository.findAll();
            List<RestrictionCodeDto> restrictionCodeDtoList = new ArrayList<>();

            for (RestrictionCode restrictionCode : restrictionCodeList) {
                restrictionCodeDtoList.add(RestrictionCodeDtoMapper.map(restrictionCode));
            }
            return ResponseEntity.ok(restrictionCodeDtoList);
        } else {
            Optional<RestrictionCode> optional = restrictionCodeRepository.findByCode(code);
            if (optional.isPresent()) {
                RestrictionCodeDto restrictionCodeDto = RestrictionCodeDtoMapper
                        .map(optional.get());
                return ResponseEntity.ok(new ArrayList<>(Collections.singletonList(restrictionCodeDto)));
            } else {
                return ResponseEntity.notFound().build();
            }

//		Link restrictionCodesLink = linkTo(methodOn(RestrictionCodeController.class).getAll()).withSelfRel();
//		Resources<RestrictionCodeDto> restrictionCodes = new Resources<RestrictionCodeDto>(restrictionCodeDtoList, restrictionCodesLink);

        }
    }
}
