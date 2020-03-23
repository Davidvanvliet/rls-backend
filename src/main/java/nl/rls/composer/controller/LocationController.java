package nl.rls.composer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.Location;
import nl.rls.composer.repository.LocationRepository;
import nl.rls.composer.rest.dto.LocationDto;
import nl.rls.composer.rest.dto.mapper.LocationDtoMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(value = "Access to Locations. ")
@RestController
@RequestMapping(BaseURL.BASE_PATH + "/locations")
public class LocationController {
    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @ApiOperation(value = "Get a location based on an id, which is the TSI.locationPrimaryCode")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDto> getById(@PathVariable Integer id) {
        Optional<Location> optional = locationRepository.findByLocationPrimaryCode(id);
        if (optional.isPresent()) {
            LocationDto locationIdentDto = LocationDtoMapper.map(optional.get());
            return ResponseEntity.ok(locationIdentDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<LocationIdentDto> getByCode(@RequestParam("code") String code) {
//		Optional<LocationIdent> optional = locationIdentRepository.findByCode(code);
//		if (optional.isPresent()) {
//			LocationIdentDto locationIdentDto = LocationIdentDtoMapper.map(optional.get());
//			return ResponseEntity.ok(locationIdentDto);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}

    @ApiOperation(value = "Get a list of locationIdent based on name, shortname of all")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationDto>> getAllQuery(
            @ApiParam(value = "name can also be a fragement of the name (TSI.primaryLocationName) [Optional]")
            @RequestParam(name = "name", required = false) String name,
            @ApiParam(value = "shortname can also be a fragement of the shortName [Optional]")
            @RequestParam(name = "shortname", required = false) String shortname) {
        Iterable<Location> locationList = null;
        if (name != null) {
            if (name.length() >= 3) {
                locationList = locationRepository.findByPrimaryLocationNameContainingIgnoreCaseOrderByPrimaryLocationNameAsc(name);
            }
        } else if (shortname != null) {
            if (shortname.length() >= 2) {
                locationList = locationRepository.findByCodeIgnoreCaseOrderByCodeAsc(shortname);
            }
        } else if (shortname == null && name == null) {
            locationList = locationRepository.findAll();
        }
        List<LocationDto> locationDtoList = new ArrayList<>();

        for (Location locationIdent : locationList) {
            locationDtoList.add(LocationDtoMapper.map(locationIdent));
        }
//		Link locationsLink = linkTo(methodOn(LocationIdentController.class).getAllQuery(name, shortname)).withSelfRel();
//		CollectionModel<LocationIdentDto> locations = new CollectionModel<LocationIdentDto>(locationDtoList, locationsLink);
        return ResponseEntity.ok(locationDtoList);
    }

//	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<CollectionModel<LocationIdentDto>> getAll() {
//
//		Iterable<LocationIdent> locationList = locationIdentRepository.findAll();
//		List<LocationIdentDto> locationDtoList = new ArrayList<>();
//
//		for (LocationIdent locationIdent : locationList) {
//			locationDtoList.add(LocationIdentDtoMapper.map(locationIdent));
//		}
//		Link locationsLink = linkTo(methodOn(LocationIdentController.class).getAll()).withSelfRel();
//		CollectionModel<LocationIdentDto> locations = new CollectionModel<LocationIdentDto>(locationDtoList, locationsLink);
//		return ResponseEntity.ok(locations);
//	}

}
