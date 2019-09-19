package nl.rls.ci.aa.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.domain.AppUser;
import nl.rls.ci.aa.domain.Owner;
import nl.rls.ci.aa.domain.Role;
import nl.rls.ci.aa.dto.UserDto;
import nl.rls.ci.aa.dto.UserDtoMapper;
import nl.rls.ci.aa.dto.UserDtoPost;
import nl.rls.ci.aa.repository.OwnerRepository;
import nl.rls.ci.aa.repository.RoleRepository;
import nl.rls.ci.aa.repository.UserRepository;

@RestController
public class SignUpController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private RoleRepository roleRepository;

	private static final Logger log = LoggerFactory.getLogger(SignUpController.class);

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public SignUpController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@PutMapping("/sign-up/{username}")
	@Transactional
	public ResponseEntity<UserDto> signUp(@PathVariable(value = "username") String username, @RequestBody UserDtoPost userDtoPost) {
		log.info("signUp: " + userDtoPost);
		AppUser user = userRepository.findByUsername(username);
		if (user != null) {
			user.setEmail(userDtoPost.getEmail());
			user.setFirstName(userDtoPost.getFirstName());
			user.setLastName(userDtoPost.getLastName());
			user.setPassword(bCryptPasswordEncoder.encode(userDtoPost.getPassword()));
		} else {
			Role role = roleRepository.findByName(Role.ROLE_USER);
			user = new AppUser();
			user.setPassword(bCryptPasswordEncoder.encode(userDtoPost.getPassword()));
			user.setUsername(username);
			user.setEmail(userDtoPost.getEmail());
			user.setFirstName(userDtoPost.getFirstName());
			user.setLastName(userDtoPost.getLastName());
			user.setEnabled(true);
			userRepository.save(user);
			user = userRepository.findById(user.getId()).get();
			System.out.println(user);
			user.getRoles().add(role);
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
