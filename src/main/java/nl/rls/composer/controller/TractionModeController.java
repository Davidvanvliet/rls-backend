package nl.rls.composer.controller;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.code.TractionMode;
import nl.rls.composer.repository.TractionModeRepository;
import nl.rls.composer.rest.dto.TractionModeDto;
import nl.rls.composer.rest.dto.mapper.TractionModeDtoMapper;
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
@RequestMapping(BaseURL.BASE_PATH + "/tractionmodes")
public class TractionModeController {
    private final TractionModeRepository tractionModeRepository;

    public TractionModeController(TractionModeRepository tractionModeRepository) {
        this.tractionModeRepository = tractionModeRepository;
    }

    @GetMapping(value = "/{tractionModeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TractionModeDto> getTractionMode(@PathVariable int tractionModeId) {
        TractionMode tractionMode = tractionModeRepository.findById(tractionModeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find traction mode with id %d", tractionModeId)));
        TractionModeDto tractionModeDto = TractionModeDtoMapper.map(tractionMode);
        return ResponseBuilder.ok()
                .data(tractionModeDto)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<TractionModeDto>> getAll(@RequestParam(value = "code", required = false) String code) {
        List<TractionMode> tractionModes;
        if (code == null) {
            tractionModes = tractionModeRepository.findAll();
        } else {
            TractionMode tractionMode = tractionModeRepository.findByCode(code)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find traction mode with code %s", code)));
            tractionModes = Collections.singletonList(tractionMode);
        }
        List<TractionModeDto> tractionModeDtos = tractionModes.stream()
                .map(TractionModeDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(tractionModeDtos)
                .build();
    }

}
