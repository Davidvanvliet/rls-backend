package nl.rls.ci.aa.controller;

import nl.rls.ci.aa.domain.AppUser;
import nl.rls.ci.aa.domain.License;
import nl.rls.ci.aa.domain.Owner;
import nl.rls.ci.aa.domain.Role;
import nl.rls.ci.aa.dto.*;
import nl.rls.ci.aa.repository.LicenseRepository;
import nl.rls.ci.aa.repository.OwnerRepository;
import nl.rls.ci.aa.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/aa/owners")
public class OwnerController {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = "/")
    public ResponseEntity<List<OwnerDto>> getAll() {
        Iterable<Owner> ownerList = ownerRepository.findAll();
        List<OwnerDto> owners = new ArrayList<OwnerDto>();
        for (Owner owner : ownerList) {
            owners.add(OwnerDtoMapper.map(owner));
        }
        return ResponseEntity.ok(owners);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable Integer id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (!owner.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(OwnerDtoMapper.map(owner.get()));
    }

    @Transactional
    @PostMapping(value = "/")
    public ResponseEntity<?> postOwner(@RequestBody OwnerDtoPost dto) {
        Owner owner = OwnerDtoMapper.map(dto);
        Calendar now = Calendar.getInstance();
        Calendar now3 = Calendar.getInstance();
        now3.add(Calendar.MONTH, 3);
        ownerRepository.save(owner);
        License license = new License(License.LICENSE_FREE, now.getTime(), now3.getTime(), owner);
        licenseRepository.save(license);
        owner.getLicenses().add(license);
        ownerRepository.save(owner);
        return ResponseEntity.created(linkTo(methodOn(OwnerController.class).getOwner(owner.getId())).toUri()).build();
    }

    @Transactional
    @PostMapping(value = "/{id}/users/")
    public ResponseEntity<?> postUser(@PathVariable Integer id, @RequestBody UserPostDto dto) {
        Optional<Owner> optional = ownerRepository.findById(id);
        if (!optional.isPresent()) {

        }
        Owner owner = optional.get();
        AppUser user = UserDtoMapper.map(dto);
        user.setOwner(owner);
        user.setUsername(dto.getEmail());
        user.setEnabled(true);
        owner.getUsers().add(user);
        Optional<Role> role = roleRepository.findByName(Role.ROLE_USER);
        user.setRole(role.get());
        ownerRepository.save(owner);
        return ResponseEntity.created(linkTo(methodOn(UserController.class).getUser(user.getId())).toUri()).build();
    }

    @GetMapping(value = "/{id}/users/")
    public ResponseEntity<List<UserDto>> getUsersByOwner(@PathVariable Integer id) {
        Optional<Owner> optional = ownerRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Owner owner = optional.get();
        List<UserDto> users = new ArrayList<UserDto>();
        for (AppUser user : owner.getUsers()) {
            users.add(UserDtoMapper.map(user));
        }
        return ResponseEntity.ok(users);
    }
}