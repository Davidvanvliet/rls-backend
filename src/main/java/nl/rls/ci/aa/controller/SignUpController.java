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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/signup")
public class SignUpController {
    private static final Logger log = LoggerFactory.getLogger(SignUpController.class);
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SignUpController(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, OwnerRepository ownerRepository, RoleRepository roleRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDto> signUp(@RequestBody UserPostDto userPostDtoPost) {
        AppUser user = new AppUser();

//        Optional<Role> role = roleRepository.findByName(Role.ROLE_USER);
//        if (role.isPresent()) {
//            user.setRole(new Role(role.get().getName()));
//        }
        user.setPassword(bCryptPasswordEncoder.encode(userPostDtoPost.getPassword()));
        user.setUsername(userPostDtoPost.getEmail());
        user.setEmail(userPostDtoPost.getEmail());
        user.setFirstName(userPostDtoPost.getFirstName());
        user.setLastName(userPostDtoPost.getLastName());
        Optional<Owner> owner = ownerRepository.findByCode(userPostDtoPost.getOwner().getCode());
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
