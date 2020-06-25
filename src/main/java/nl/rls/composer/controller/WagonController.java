package nl.rls.composer.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasPermission('read:wagon')")
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
    @PreAuthorize("hasPermission('read:wagon')")
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
    @PreAuthorize("hasPermission('write:wagon')")
    public Response<WagonDto> createWagon(@RequestBody @Valid WagonPostDto wagonPostDto) {
        int ownerId = securityContext.getOwnerId();
        Wagon wagon = new Wagon();
        wagon.setOwnerId(ownerId);
        WagonDto processedWagon = processWagon(wagon, wagonPostDto);

        return ResponseBuilder.created()
                .data(processedWagon)
                .build();
    }

    @PutMapping(value = "/{wagonId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission('write:wagon')")
    public Response<WagonDto> editWagon(@PathVariable Integer wagonId, @RequestBody @Valid WagonPostDto wagonPostDto) {
        int ownerId = securityContext.getOwnerId();
        Wagon wagon = wagonRepository.findByIdAndOwnerId(wagonId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon with id %d", wagonId)));
        WagonDto processedWagon = processWagon(wagon, wagonPostDto);

        return ResponseBuilder.created()
                .data(processedWagon)
                .build();
    }

    private WagonDto processWagon(Wagon wagon, WagonPostDto wagonPostDto) {
        wagon.setNumberFreight(wagonPostDto.getNumberFreight());
        wagon.setCode(wagonPostDto.getCode());
        wagon.setBrakeWeightG(wagonPostDto.getBrakeWeightG());
        wagon.setBrakeWeightP(wagonPostDto.getBrakeWeightP());
        wagon.setLengthOverBuffers(wagonPostDto.getLengthOverBuffers());
        wagon.setTypeName(wagonPostDto.getTypeName());
        wagon.setWagonNumberOfAxles(wagonPostDto.getNumberOfAxles());
        wagon.setWagonWeightEmpty(wagonPostDto.getWeightEmpty());
        wagon.setMaxSpeed(wagonPostDto.getMaxSpeed());
        Wagon updatedWagon = wagonRepository.save(wagon);
        return WagonDtoMapper.map(updatedWagon);
    }

}
