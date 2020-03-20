package nl.rls.composer.controller;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Traction;
import nl.rls.composer.domain.code.TractionMode;
import nl.rls.composer.domain.code.TractionType;
import nl.rls.composer.repository.TractionModeRepository;
import nl.rls.composer.repository.TractionRepository;
import nl.rls.composer.repository.TractionTypeRepository;
import nl.rls.composer.rest.dto.TractionCreateDto;
import nl.rls.composer.rest.dto.TractionDto;
import nl.rls.composer.rest.dto.mapper.TractionDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/tractions")
public class TractionController {
    private final TractionRepository tractionRepository;
    private final TractionTypeRepository tractionTypeRepository;
    private final TractionModeRepository tractionModeRepository;
    private final SecurityContext securityContext;

    public TractionController(TractionRepository tractionRepository, TractionTypeRepository tractionTypeRepository, TractionModeRepository tractionModeRepository, SecurityContext securityContext) {
        this.tractionRepository = tractionRepository;
        this.tractionTypeRepository = tractionTypeRepository;
        this.tractionModeRepository = tractionModeRepository;
        this.securityContext = securityContext;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<TractionDto>> getAll() {
        int ownerId = securityContext.getOwnerId();
        List<Traction> tractionList = tractionRepository.findByOwnerId(ownerId);
        List<TractionDto> tractionDtos = tractionList.stream()
                .map(TractionDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(tractionDtos)
                .build();
    }

    @RequestMapping(value = "/{tractionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TractionDto> getById(@PathVariable int tractionId) {
        int ownerId = securityContext.getOwnerId();
        Traction traction = tractionRepository.findByIdAndOwnerId(tractionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find traction with id %d", tractionId)));
        TractionDto tractionDto = TractionDtoMapper.map(traction);
        return ResponseBuilder.ok()
                .data(tractionDto)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TractionDto> createTraction(@RequestBody @Valid TractionCreateDto tractionDto) {
        int ownerId = securityContext.getOwnerId();
        Traction traction = TractionDtoMapper.map(tractionDto);
        traction.setOwnerId(ownerId);

        int tractionTypeId = DecodePath.decodeInteger(tractionDto.getTractionType(), "tractiontypes");
        Optional<TractionType> tractionType = tractionTypeRepository.findById(tractionTypeId);
        if (tractionType.isPresent()) {
            traction.setTractionType(tractionType.get());
        }
        int tractionModeId = DecodePath.decodeInteger(tractionDto.getTractionMode(), "tractionmodes");
        Optional<TractionMode> tractionMode = tractionModeRepository.findById(tractionModeId);
        if (tractionMode.isPresent()) {
            traction.setTractionMode(tractionMode.get());
        }
        tractionRepository.save(traction);
        TractionDto resultDto = TractionDtoMapper.map(traction);
        return ResponseBuilder.created()
                .data(resultDto)
                .build();
    }


}
