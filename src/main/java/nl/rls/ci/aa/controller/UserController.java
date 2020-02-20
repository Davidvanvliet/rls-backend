package nl.rls.ci.aa.controller;

import nl.rls.ci.aa.domain.AppUser;
import nl.rls.ci.aa.domain.Owner;
import nl.rls.ci.aa.domain.Role;
import nl.rls.ci.aa.dto.UserDto;
import nl.rls.ci.aa.dto.UserDtoMapper;
import nl.rls.ci.aa.dto.UserPostDto;
import nl.rls.ci.aa.repository.OwnerRepository;
import nl.rls.ci.aa.repository.RoleRepository;
import nl.rls.ci.aa.repository.UserRepository;
import nl.rls.ci.aa.security.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aa/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final RoleRepository roleRepository;
    private final SecurityContext securityContext;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, OwnerRepository ownerRepository, RoleRepository roleRepository, SecurityContext securityContext, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.roleRepository = roleRepository;
        this.securityContext = securityContext;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        log.debug("public List<UserDto> getAll()");
        Iterable<AppUser> userList = userRepository.findAll();
        List<UserDto> users = new ArrayList<>();
        for (AppUser user : userList) {
            users.add(UserDtoMapper.map(user));
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
        Optional<AppUser> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserDtoMapper.map(user.get()));
    }

    @PutMapping("/{username}")
    @Transactional
    public ResponseEntity<UserDto> signUp(@PathVariable(value = "username") String username,
                                          @RequestBody UserPostDto userPostDtoPost) {
        int ownerId = securityContext.getOwnerId();
        log.info("signUp: " + userPostDtoPost);
        AppUser user = userRepository.findByUsername(username);
        if (user != null) {
            user.setFirstName(userPostDtoPost.getFirstName());
            user.setLastName(userPostDtoPost.getLastName());
            user.setPassword(bCryptPasswordEncoder.encode(userPostDtoPost.getPassword()));
        } else {
            Optional<Role> role = roleRepository.findByName(Role.ROLE_USER);
            user = new AppUser();
            user.setPassword(bCryptPasswordEncoder.encode(userPostDtoPost.getPassword()));
            user.setUsername(userPostDtoPost.getEmail());
            user.setEmail(userPostDtoPost.getEmail());
            user.setFirstName(userPostDtoPost.getFirstName());
            user.setLastName(userPostDtoPost.getLastName());
            user.setEnabled(true);
            user.setRole(role.get());
            log.info("signUp: getOwner");
            Optional<Owner> optional = ownerRepository.findById(ownerId);
            optional.get().getUsers().add(user);
            user.setOwner(optional.get());
        }
        log.info("signUp: saving ...");
        userRepository.save(user);
        return ResponseEntity.ok(UserDtoMapper.map(user));
    }

    @PutMapping("/{userId}/role/{roleName}")
    public ResponseEntity<UserDto> setRoleToUser(@PathVariable(value = "userId") int userId,
                                                 @PathVariable(value = "roleName") String roleName) {
        log.info("setRoleToUser: " + userId + " " + roleName);
        Optional<AppUser> optional = userRepository.findById(userId);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        AppUser user = optional.get();
        log.info("setRoleToUser: user = " + user);
        Optional<Role> role = roleRepository.findByName(roleName);
        if (!role.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        log.info("setRoleToUser: role = " + role.get().getName());
        user.setRole(role.get());
        userRepository.save(user);
        return ResponseEntity.ok(UserDtoMapper.map(user));
    }
}