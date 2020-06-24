package nl.rls.composer.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.Traction;
import nl.rls.composer.domain.code.TractionType;
import nl.rls.composer.repository.TractionRepository;
import nl.rls.composer.repository.TractionTypeRepository;
import nl.rls.composer.rest.dto.TractionCreateDto;
import nl.rls.composer.rest.dto.TractionDto;
import nl.rls.composer.rest.dto.mapper.TractionDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/tractions")
public class TractionController {
    private final TractionRepository tractionRepository;
    private final TractionTypeRepository tractionTypeRepository;
    private final SecurityContext securityContext;

    public TractionController(TractionRepository tractionRepository, TractionTypeRepository tractionTypeRepository, SecurityContext securityContext) {
        this.tractionRepository = tractionRepository;
        this.tractionTypeRepository = tractionTypeRepository;
        this.securityContext = securityContext;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasPermission('view:trains')")
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

    @GetMapping(value = "/{tractionId}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public Response<TractionDto> createTraction(@RequestBody @Valid TractionCreateDto tractionCreateDto) {
        int ownerId = securityContext.getOwnerId();
        Traction traction = new Traction();
        traction.setOwnerId(ownerId);
        TractionDto processedTraction = processTraction(traction, tractionCreateDto);

        return ResponseBuilder.created()
                .data(processedTraction)
                .build();
    }

    @PutMapping(value = "/{tractionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<TractionDto> updateTraction(@PathVariable Integer tractionId, @RequestBody @Valid TractionCreateDto tractionCreateDto) {
        int ownerId = securityContext.getOwnerId();
        Traction traction = tractionRepository.findByIdAndOwnerId(tractionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find traction with id %d", tractionId)));

        TractionDto processedTraction = processTraction(traction, tractionCreateDto);
        return ResponseBuilder.created()
                .data(processedTraction)
                .build();
    }

    private TractionDto processTraction(Traction traction, TractionCreateDto tractionCreateDto) {
        traction.setBrakeWeightG(tractionCreateDto.getBrakeWeightG());
        traction.setBrakeWeightP(tractionCreateDto.getBrakeWeightP());
        traction.setLengthOverBuffers(tractionCreateDto.getLengthOverBuffers());
        traction.setLocoNumber(tractionCreateDto.getLocoNumber());
        traction.setLocoTypeNumber(tractionCreateDto.getLocoTypeNumber());
        traction.setNumberOfAxles(tractionCreateDto.getNumberOfAxles());
        Optional<TractionType> tractionType = tractionTypeRepository.findByCode(tractionCreateDto.getTractionType());
        tractionType.ifPresent(traction::setTractionType);
        traction.setTypeName(tractionCreateDto.getTypeName());
        traction.setWeight(tractionCreateDto.getWeight());
        Traction updatedTraction = tractionRepository.save(traction);
        return TractionDtoMapper.map(updatedTraction);
    }

}
