package nl.rls.ci.aa.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nl.rls.ci.aa.domain.AppUser;
import nl.rls.ci.aa.domain.Role;
import nl.rls.ci.aa.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	/**
	 * Load the user from a DB
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername - username: " + username);
		AppUser user = userRepository.findByUsername(username);
		if (user != null) {
			System.out.println("OwnerId: " + user.getOwner().getId());
			UserDetails userDetails = new org.springframework.security.core.userdetails.User(
					user.getUsername(), user.getPassword(),
					getAuthorities(user.getRole()));
			// emptyList());
			return userDetails;
		} else {
			throw new IllegalArgumentException();
		}
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
		return getGrantedAuthorities(role);
	}

	private List<GrantedAuthority> getGrantedAuthorities(Role role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.getName()));
		return authorities;
	}
}