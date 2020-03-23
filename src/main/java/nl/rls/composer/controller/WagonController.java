package nl.rls.composer.controller;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.WagonDto;
import nl.rls.composer.rest.dto.WagonPostDto;
import nl.rls.composer.rest.dto.mapper.WagonDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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
    public Response<WagonDto> create(@RequestBody WagonPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        Wagon entity = new Wagon();
        entity.setOwnerId(ownerId);
        entity.setNumberFreight(dto.getNumberFreight());
        entity.setCode(dto.getCode());
        entity.setHandBrakeBrakedWeight(dto.getHandBrakeBrakedWeight());
        entity.setLengthOverBuffers(dto.getLengthOverBuffers());
        entity.setName(dto.getName());
        entity.setWagonNumberOfAxles(dto.getWagonNumberOfAxles());
        entity.setWagonWeightEmpty(dto.getWagonWeightEmpty());
        wagonRepository.save(entity);
        WagonDto wagonDto = WagonDtoMapper.map(entity);
        return ResponseBuilder.created()
                .data(wagonDto)
                .build();
    }

}
