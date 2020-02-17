package nl.rls.composer.controller;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.code.RestrictionCode;
import nl.rls.composer.repository.RestrictionCodeRepository;
import nl.rls.composer.rest.dto.RestrictionCodeDto;
import nl.rls.composer.rest.dto.mapper.RestrictionCodeDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "restrictioncodes")
public class RestrictionCodeController {
    @Autowired
    private RestrictionCodeRepository restrictionCodeRepository;

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

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestrictionCodeDto> getRestrictionCodeByCode(@RequestParam("code") String code) {
        Optional<RestrictionCode> optional = restrictionCodeRepository.findByCode(code);
        if (optional.isPresent()) {
            RestrictionCodeDto restrictionCodeDto = RestrictionCodeDtoMapper
                    .map(optional.get());
            return ResponseEntity.ok(restrictionCodeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestrictionCodeDto>> getAll() {
        Iterable<RestrictionCode> restrictionCodeList = restrictionCodeRepository.findAll();
        List<RestrictionCodeDto> restrictionCodeDtoList = new ArrayList<>();

        for (RestrictionCode restrictionCode : restrictionCodeList) {
            restrictionCodeDtoList.add(RestrictionCodeDtoMapper.map(restrictionCode));
        }
//		Link restrictionCodesLink = linkTo(methodOn(RestrictionCodeController.class).getAll()).withSelfRel();
//		Resources<RestrictionCodeDto> restrictionCodes = new Resources<RestrictionCodeDto>(restrictionCodeDtoList, restrictionCodesLink);
        return ResponseEntity.ok(restrictionCodeDtoList);
    }

}
