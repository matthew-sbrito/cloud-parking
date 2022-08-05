package com.techsoft.parking.authentication.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import com.techsoft.parking.authentication.domain.ApplicationUser;
import com.techsoft.parking.authentication.dto.response.ApplicationUserDTO;
import com.techsoft.parking.authentication.dto.response.AuthenticateDTO;
import com.techsoft.parking.authentication.security.token.TokenBuilder;
import com.techsoft.parking.common.properties.JwtConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Matheus Brito
 */
@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final TokenBuilder tokenBuilder;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfiguration jwtConfiguration, TokenBuilder tokenBuilder) {
        super(authenticationManager);

        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
        this.tokenBuilder = tokenBuilder;
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("Attempting authentication. . .");
        ApplicationUser applicationUser = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);

        if(applicationUser == null){
            throw new UsernameNotFoundException("Unable to retrieve the username or password!");
        }

        log.info("Creating the authentication object for the user '{}' and calling UserDetailServiceImpl loadUserByUsername", applicationUser);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                applicationUser.getUsername(), applicationUser.getPassword(), applicationUser.getAuthorities()
        );

        usernamePasswordAuthenticationToken.setDetails(applicationUser);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    private AuthenticateDTO generateAuthResponse(Authentication authentication, String token) {
        log.info("Creating response auth for user!");
        ApplicationUser user = (ApplicationUser) authentication.getPrincipal();

        AuthenticateDTO authenticateDTO = new AuthenticateDTO();

        authenticateDTO.setCreateAt(new Date());
        authenticateDTO.setExpireIn(jwtConfiguration.getExpiration());
        authenticateDTO.setUser(new ApplicationUserDTO(user));
        authenticateDTO.setToken(token);

        return authenticateDTO;
    }

    @Override
    @SneakyThrows
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        log.info("Authentication was successful for the user '{}', generating JWT token", auth.getName());

        SignedJWT signedJWT = tokenBuilder.createSignedJWT(auth);

        String encryptedToken = tokenBuilder.encryptToken(signedJWT);

        AuthenticateDTO authenticateDTO = generateAuthResponse(auth, encryptedToken);

        log.info("Token generated successfully, adding it to response");

        response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + jwtConfiguration.getHeader().getName());
        response.addHeader(jwtConfiguration.getHeader().getName(), jwtConfiguration.getHeader().getPrefix() + " " + encryptedToken);
        response.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(authenticateDTO.toJson());
        response.getWriter().flush();
    }
}
