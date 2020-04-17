package nl.rls.ci.aa.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


@RestController
@RequestMapping("/signup")
public class SignUpController {
    private static final Logger log = LoggerFactory.getLogger(SignUpController.class);
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecurityContext securityContext;

    public SignUpController(BCryptPasswordEncoder bCryptPasswordEncoder, SecurityContext securityContext, UserRepository userRepository, OwnerRepository ownerRepository, RoleRepository roleRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.roleRepository = roleRepository;
        this.securityContext = securityContext;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDto> signUp(@RequestBody UserPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        AppUser user = new AppUser();

        Optional<Role> role = roleRepository.findByName(Role.ROLE_USER);
        if (role.isPresent()) {
            user.setRole(new Role(role.get().getName()));
        }
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setUsername(dto.getEmail());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        owner.ifPresent(user::setOwner);
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok(UserDtoMapper.map(user));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserPostDto userPostDtoPost) {
        return null;
    }
}
