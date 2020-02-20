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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("sign-up/")
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

    @PutMapping("{username}")
    @Transactional
    public ResponseEntity<UserDto> signUp(@PathVariable(value = "username") String username, @RequestBody UserPostDto userPostDtoPost) {
        log.info("signUp: " + userPostDtoPost);
        AppUser user = userRepository.findByUsername(username);
        if (user != null) {
            user.setEmail(userPostDtoPost.getEmail());
            user.setFirstName(userPostDtoPost.getFirstName());
            user.setLastName(userPostDtoPost.getLastName());
            user.setPassword(bCryptPasswordEncoder.encode(userPostDtoPost.getPassword()));
        } else {
            Optional<Role> role = roleRepository.findByName(Role.ROLE_USER);
            user = new AppUser();
            user.setPassword(bCryptPasswordEncoder.encode(userPostDtoPost.getPassword()));
            user.setUsername(username);
            user.setEmail(userPostDtoPost.getEmail());
            user.setFirstName(userPostDtoPost.getFirstName());
            user.setLastName(userPostDtoPost.getLastName());
            user.setEnabled(true);
            userRepository.save(user);
            user = userRepository.findById(user.getId()).get();
            System.out.println(user);
            user.setRole(role.get());
            log.info("signUp: getOwner");
            Optional<Owner> optional = ownerRepository.findById(1);
            optional.get().getUsers().add(user);
            user.setOwner(optional.get());
        }
        log.info("signUp: saving ...");
        userRepository.save(user);
        return ResponseEntity.ok(UserDtoMapper.map(user));
    }

}
