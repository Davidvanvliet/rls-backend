package nl.rls.composer.controller;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.code.RestrictionCode;
import nl.rls.composer.repository.RestrictionCodeRepository;
import nl.rls.composer.rest.dto.RestrictionCodeDto;
import nl.rls.composer.rest.dto.mapper.RestrictionCodeDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/restrictioncodes")
public class RestrictionCodeController {
    private final RestrictionCodeRepository restrictionCodeRepository;

    public RestrictionCodeController(RestrictionCodeRepository restrictionCodeRepository) {
        this.restrictionCodeRepository = restrictionCodeRepository;
    }

    @GetMapping(value = "/{restrictionCodeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<RestrictionCodeDto> getRestrictionCode(@PathVariable int restrictionCodeId) {
        RestrictionCode restrictionCode = restrictionCodeRepository.findById(restrictionCodeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find restriction code with id %d", restrictionCodeId)));
        RestrictionCodeDto restrictionCodeDto = RestrictionCodeDtoMapper
                .map(restrictionCode);
        return ResponseBuilder.ok()
                .data(restrictionCodeDto)
                .build();
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<RestrictionCodeDto>> getAll(@RequestParam(value = "code", required = false) String code) {
        List<RestrictionCode> restrictionCodes;
        if (code == null) {
            restrictionCodes = restrictionCodeRepository.findAll();
        } else {
            RestrictionCode restrictionCode = restrictionCodeRepository.findByCode(code)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find restriction code %s", code)));
            restrictionCodes = Collections.singletonList(restrictionCode);
        }
        List<RestrictionCodeDto> restrictionCodeDtos = restrictionCodes.stream()
                .map(RestrictionCodeDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(restrictionCodeDtos)
                .build();
    }
}
