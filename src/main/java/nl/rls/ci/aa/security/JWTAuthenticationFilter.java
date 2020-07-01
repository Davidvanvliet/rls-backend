package nl.rls.ci.aa.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.rls.ci.aa.domain.License;
import nl.rls.ci.aa.dto.UserDtoLogin;
import nl.rls.ci.aa.repository.LicenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static nl.rls.ci.aa.security.SecurityConstants.*;

/**
 * @author berend.wilkens
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private final AuthenticationManager authenticationManager;
    private final LicenseRepository licenseRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, LicenseRepository licenseRepository) {
        this.authenticationManager = authenticationManager;
        this.licenseRepository = licenseRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            UserDtoLogin userDtoLogin = new ObjectMapper().readValue(req.getInputStream(), UserDtoLogin.class);

            log.info("UserDtoLogin: " + userDtoLogin);

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDtoLogin.getUsername(),
                    userDtoLogin.getPassword(), new ArrayList<>());

            if (authentication.isAuthenticated()) {
                if (authentication.getPrincipal().equals("berend.wilkens@hu.nl")) {
                    return authenticationManager.authenticate(authentication);
                }
                List<License> licenses = licenseRepository.validLicenseByUsername(userDtoLogin.getUsername(),
                        new Date());
                if (licenses.size() > 0) {
                    return authenticationManager.authenticate(authentication);
                }
                authentication.setAuthenticated(false);
                return authentication;
            }
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        if (auth.isAuthenticated()) {
            String role = "USER";
            for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
                role = grantedAuthority.getAuthority();
            }
            String token = JWT.create()
                    .withSubject(
                            ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                    .withClaim("role", role)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(HMAC512(SECRET.getBytes()));
            res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        }
    }
}
