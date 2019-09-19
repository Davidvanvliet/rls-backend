package nl.rls.ci.aa.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.domain.AppUser;
import nl.rls.ci.aa.domain.Role;
import nl.rls.ci.aa.dto.RoleDto;
import nl.rls.ci.aa.dto.RoleDtoMapper;
import nl.rls.ci.aa.dto.UserDto;
import nl.rls.ci.aa.dto.UserDtoMapper;
import nl.rls.ci.aa.repository.RoleRepository;
import nl.rls.ci.aa.repository.UserRepository;

@RestController
@RequestMapping("/aa/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resources<UserDto>> getAll() {
		log.debug("public List<UserDto> getAll()");
		Iterable<AppUser> userList = userRepository.findAll();
		Collection<UserDto> users = new ArrayList<UserDto>();
		for (AppUser user : userList) {
			users.add(UserDtoMapper.map(user));
		}
		Link usersLink = linkTo(methodOn(UserController.class).getAll()).withSelfRel();
		Resources<UserDto> resourceList = new Resources<UserDto>(users, usersLink);
		return ResponseEntity.ok(resourceList);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDto> getUser(@PathVariable int id) {
		Optional<AppUser> user = userRepository.findById(id);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(UserDtoMapper.map(user.get()));
	}

	@GetMapping("/{userId}/roles")
	public ResponseEntity<Resources<RoleDto>> getRolesByUser(@PathVariable int userId) {
		Optional<AppUser> optional = userRepository.findById(userId);
		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		AppUser user = optional.get();
		List<RoleDto> roles = new ArrayList<>();
		for (Role role : user.getRoles()) {
			roles.add(RoleDtoMapper.map(role));
		}
		Link rolesLink = linkTo(methodOn(UserController.class).getRolesByUser(userId)).withSelfRel();
		Resources<RoleDto> resourceList = new Resources<RoleDto>(roles, rolesLink);
		return ResponseEntity.ok(resourceList);
	}

	@PutMapping("/{userId}/roles/{roleName}")
	public ResponseEntity<?> addRoleToUser(@PathVariable(value = "userId") int userId,
			@PathVariable(value = "roleName") String roleName) {
		Optional<AppUser> optional = userRepository.findById(userId);
		if (optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		AppUser user = optional.get();
		Role role = new Role(roleName);
		if (user.getRoles().contains(role)) {
			return ResponseEntity.ok(null);
		}
		role = roleRepository.findByName(roleName);
		if (role == null) {
			return ResponseEntity.notFound().build();
		}
		user.getRoles().add(role);
		userRepository.save(user);
		return ResponseEntity.ok(null);
	}
}