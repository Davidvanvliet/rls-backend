package nl.rls.ci.aa.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import nl.rls.ci.aa.domain.AppUser;
import nl.rls.ci.aa.domain.License;
import nl.rls.ci.aa.domain.Owner;
import nl.rls.ci.aa.domain.Role;
import nl.rls.ci.aa.dto.OwnerDto;
import nl.rls.ci.aa.dto.OwnerDtoMapper;
import nl.rls.ci.aa.dto.OwnerDtoPost;
import nl.rls.ci.aa.dto.UserDto;
import nl.rls.ci.aa.dto.UserDtoMapper;
import nl.rls.ci.aa.dto.UserPostDto;
import nl.rls.ci.aa.repository.LicenseRepository;
import nl.rls.ci.aa.repository.OwnerRepository;
import nl.rls.ci.aa.repository.RoleRepository;
import nl.rls.ci.aa.repository.UserRepository;
import nl.rls.ci.url.BaseURL;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;

import javax.persistence.EntityNotFoundException;


@RestController
@RequestMapping(BaseURL.BASE_PATH + "/owners")
@Api(value = "Access to owners and register new owners with first license")
public class OwnerController {
    private final OwnerRepository ownerRepository;
    private final LicenseRepository licenseRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public OwnerController(OwnerRepository ownerRepository, UserRepository userRepository, LicenseRepository licenseRepository, RoleRepository roleRepository) {
        this.ownerRepository = ownerRepository;
        this.licenseRepository = licenseRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAll() {
        Iterable<Owner> ownerList = ownerRepository.findAll();
        List<OwnerDto> owners = new ArrayList<>();
        for (Owner owner : ownerList) {
            owners.add(OwnerDtoMapper.map(owner));
        }
        return ResponseEntity.ok(owners);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable Integer id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        return owner.map(value -> ResponseEntity.ok(OwnerDtoMapper.map(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<OwnerDto> postOwner(@RequestBody OwnerDtoPost dto) {
        Owner owner = OwnerDtoMapper.map(dto);
        Calendar now = Calendar.getInstance();
        Calendar now3 = Calendar.getInstance();
        now3.add(Calendar.MONTH, 3);
        ownerRepository.save(owner);
        License license = new License(License.LICENSE_FREE, now.getTime(), now3.getTime(), owner);
        licenseRepository.save(license);
        owner.getLicenses().add(license);
        ownerRepository.save(owner);
        OwnerDto ownerDto = OwnerDtoMapper.map(owner);
//        URI contentLocation = linkTo(methodOn(OwnerController.class).getOwner(owner.getId())).toUri();
        return ResponseBuilder.created()
                .data(ownerDto)
                .build();

    }

    @Transactional
    @PostMapping(value = "/{id}/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> postUser(@PathVariable Integer id, @RequestBody UserPostDto dto) {
        Optional<Owner> optional = ownerRepository.findById(id);
        if (!optional.isPresent()) {
        	throw new EntityNotFoundException();
        }
        AppUser user = UserDtoMapper.map(dto);
        user.setOwner(optional.get());
        user.setUsername(dto.getEmail());
        user.setEnabled(true);
        Optional<Role> role = roleRepository.findByName(Role.ROLE_USER);
        if (!role.isPresent()) {
        	System.out.println("Role not found");
        }
        user.setRole(role.get());
        userRepository.save(user);
        return ResponseEntity.created(linkTo(methodOn(UserController.class).getUser(user.getId())).toUri()).build();
    }

    @GetMapping(value = "{id}/users/")
    @ResponseStatus(HttpStatus.OK)
    public Response<List<UserDto>> getUsersByOwner(@PathVariable Integer id) {
        Optional<Owner> optional = ownerRepository.findById(id);
        if (!optional.isPresent()) {
            throw new EntityNotFoundException("Owner not found with id = "+id.toString());
        }
        Owner owner = optional.get();
        List<UserDto> users = new ArrayList<>();
        for (AppUser user : owner.getUsers()) {
            users.add(UserDtoMapper.map(user));
        }
        return ResponseBuilder.ok()
                .data(users)
                .build();
    }
}
