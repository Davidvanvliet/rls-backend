package nl.rls.composer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.Location;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.rest.dto.LocationIdentDto;
import nl.rls.composer.rest.dto.mapper.LocationIdentDtoMapper;

@Api(value = "Access to Locations. ")
@RestController
@RequestMapping(BaseURL.BASE_PATH+"locations")
public class LocationIdentController {
	@Autowired
	private LocationIdentRepository locationIdentRepository;
	@ApiOperation(value = "Get a location based on an id, which is the TSI.locationPrimaryCode")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LocationIdentDto> getById(@PathVariable Integer id) {
		Optional<Location> optional = locationIdentRepository.findByLocationPrimaryCode(id);
		if (optional.isPresent()) {
			LocationIdentDto locationIdentDto = LocationIdentDtoMapper.map(optional.get());
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
	public ResponseEntity<List<LocationIdentDto>> getAllQuery(
			@ApiParam(value = "name can also be a fragement of the name (TSI.primaryLocationName) [Optional]")
			@RequestParam(name = "name", required = false) String name, 
			@ApiParam(value = "shortname can also be a fragement of the shortName [Optional]")
			@RequestParam(name= "shortname", required = false) String shortname) {
		Iterable<Location> locationList = null;
		if (name != null) {
			if (name.length() >= 3) {
				locationList = locationIdentRepository.findByPrimaryLocationNameContainingIgnoreCase(name);
			}
		} else if (shortname != null) {
			if (shortname.length() >= 2) {
				locationList = locationIdentRepository.findByCodeIgnoreCase(shortname);
			}
		} else if (shortname == null && name == null) {
			locationList = locationIdentRepository.findAll();			
		}
		List<LocationIdentDto> locationDtoList = new ArrayList<>();

		for (Location locationIdent : locationList) {
			locationDtoList.add(LocationIdentDtoMapper.map(locationIdent));
		}
//		Link locationsLink = linkTo(methodOn(LocationIdentController.class).getAllQuery(name, shortname)).withSelfRel();
//		Resources<LocationIdentDto> locations = new Resources<LocationIdentDto>(locationDtoList, locationsLink);
		return ResponseEntity.ok(locationDtoList);
	}

//	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Resources<LocationIdentDto>> getAll() {
//
//		Iterable<LocationIdent> locationList = locationIdentRepository.findAll();
//		List<LocationIdentDto> locationDtoList = new ArrayList<>();
//
//		for (LocationIdent locationIdent : locationList) {
//			locationDtoList.add(LocationIdentDtoMapper.map(locationIdent));
//		}
//		Link locationsLink = linkTo(methodOn(LocationIdentController.class).getAll()).withSelfRel();
//		Resources<LocationIdentDto> locations = new Resources<LocationIdentDto>(locationDtoList, locationsLink);
//		return ResponseEntity.ok(locations);
//	}

}
