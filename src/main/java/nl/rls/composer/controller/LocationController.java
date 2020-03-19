package nl.rls.composer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.Location;
import nl.rls.composer.repository.LocationRepository;
import nl.rls.composer.rest.dto.LocationDto;
import nl.rls.composer.rest.dto.mapper.LocationDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Access to Locations.")
@RestController
@RequestMapping(BaseURL.BASE_PATH + "/locations")
public class LocationController {
    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @ApiOperation(value = "Get a location based on an id, which is the TSI.locationPrimaryCode")
    @GetMapping(value = "/{locationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<LocationDto> getById(@PathVariable int locationId) {
        Location location = locationRepository.findByLocationPrimaryCode(locationId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find location with id %s", locationId)));
        LocationDto locationIdentDto = LocationDtoMapper.map(location);
        return ResponseBuilder.ok()
                .data(locationIdentDto)
                .build();
    }

    @ApiOperation(value = "Get a list of locationIdent based on name, shortname of all")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<LocationDto>> getAllQuery(
            @ApiParam(value = "name can also be a fragement of the name (TSI.primaryLocationName) [Optional]")
            @RequestParam(name = "name", required = false) String name,
            @ApiParam(value = "shortname can also be a fragement of the shortName [Optional]")
            @RequestParam(name = "shortname", required = false) String shortname) {
        List<Location> locationList = new ArrayList<>();
        if (name != null) {
            if (name.length() >= 3) {
                locationList = locationRepository.findByPrimaryLocationNameContainingIgnoreCaseOrderByPrimaryLocationNameAsc(name);
            }
        } else if (shortname != null) {
            if (shortname.length() >= 2) {
                locationList = locationRepository.findByCodeIgnoreCaseOrderByCodeAsc(shortname);
            }
        } else {
            locationList = locationRepository.findAll();
        }
        List<LocationDto> locationDtoList = locationList.stream()
                .map(LocationDtoMapper::map)
                .collect(Collectors.toList());

        return ResponseBuilder.ok()
                .data(locationDtoList)
                .build();
    }

}
