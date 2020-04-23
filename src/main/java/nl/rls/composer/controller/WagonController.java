package nl.rls.composer.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.WagonDto;
import nl.rls.composer.rest.dto.WagonPostDto;
import nl.rls.composer.rest.dto.mapper.WagonDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/wagons")
public class WagonController {
    private final WagonRepository wagonRepository;
    private final SecurityContext securityContext;

    public WagonController(WagonRepository wagonRepository, SecurityContext securityContext) {
        this.wagonRepository = wagonRepository;
        this.securityContext = securityContext;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<WagonDto>> getAll() {
        int ownerId = securityContext.getOwnerId();
        List<Wagon> wagons = wagonRepository.findByOwnerId(ownerId);
        List<WagonDto> wagonDtos = wagons.stream()
                .map(WagonDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(wagonDtos)
                .build();
    }

    @GetMapping(value = "/{wagonId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<WagonDto> getById(@PathVariable int wagonId) {
        int ownerId = securityContext.getOwnerId();
        Wagon wagon = wagonRepository.findByIdAndOwnerId(wagonId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon with id %d", wagonId)));
        WagonDto dto = WagonDtoMapper.map(wagon);
        return ResponseBuilder.ok()
                .data(dto)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<WagonDto> createWagon(@RequestBody @Valid WagonPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        Wagon entity = new Wagon();
        entity.setOwnerId(ownerId);
        entity.setNumberFreight(dto.getNumberFreight());
        entity.setCode(dto.getCode());
        entity.setBrakeWeightG(dto.getBrakeWeightG());
        entity.setBrakeWeightP(dto.getBrakeWeightP());
        entity.setLengthOverBuffers(dto.getLengthOverBuffers());
        entity.setTypeName(dto.getTypeName());
        entity.setWagonNumberOfAxles(dto.getNumberOfAxles());
        entity.setWagonWeightEmpty(dto.getWeightEmpty());
        entity.setMaxSpeed(dto.getMaxSpeed());
        wagonRepository.save(entity);
        WagonDto wagonDto = WagonDtoMapper.map(entity);
        return ResponseBuilder.created()
                .data(wagonDto)
                .build();
    }

}
