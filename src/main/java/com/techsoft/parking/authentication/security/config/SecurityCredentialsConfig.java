package com.techsoft.parking.authentication.security.config;

import com.techsoft.parking.authentication.security.filter.JwtTokenAuthorizationFilter;
import com.techsoft.parking.authentication.security.filter.JwtUsernameAndPasswordAuthenticationFilter;
import com.techsoft.parking.authentication.security.token.TokenBuilder;
import com.techsoft.parking.authentication.security.token.TokenParser;
import com.techsoft.parking.authentication.service.UserDetailsServiceImpl;
import com.techsoft.parking.common.properties.JwtConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;

/**
 * Configuring Http Request for authentication login with JWT!
 * Also configure beans of security for application
 * @author Matheus Brito
 * @since 02/08/2022
 */
@Configuration
@EnableWebSecurity
public class SecurityCredentialsConfig {
    private final AuthenticationConfiguration configuration;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtConfiguration jwtConfiguration;
    private final TokenBuilder tokenBuilder;
    private final TokenParser tokenParser;

    @Autowired
    public SecurityCredentialsConfig(
            AuthenticationConfiguration configuration,
            UserDetailsServiceImpl userDetailsService,
            JwtConfiguration jwtConfiguration,
            TokenBuilder tokenBuilder,
            TokenParser tokenParser
    ) {
        this.configuration = configuration;
        this.userDetailsService = userDetailsService;
        this.jwtConfiguration = jwtConfiguration;
        this.tokenBuilder = tokenBuilder;
        this.tokenParser = tokenParser;
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManager() {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
//        JwtUsernameAndPasswordAuthenticationFilter filterGenerateJWT = new JwtUsernameAndPasswordAuthenticationFilter(
//                authenticationManager(), jwtConfiguration, tokenBuilder
//        );
//
//        JwtTokenAuthorizationFilter filterParserJWT = new JwtTokenAuthorizationFilter(
//                jwtConfiguration, tokenParser
//        );
//
//        httpSecurity
//                .csrf().disable()
//                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().exceptionHandling()
//                .authenticationEntryPoint((request, response, authenticationException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
//                .and().addFilter(filterGenerateJWT)
//                .addFilterAfter(filterParserJWT, UsernamePasswordAuthenticationFilter.class)
//                .authenticationProvider(authenticationProvider())
//                .authenticationManager(authenticationManager())
//                .authorizeRequests()
//                .antMatchers(jwtConfiguration.getLoginURL()).permitAll()
//                .anyRequest().authenticated();

        return httpSecurity.build();
    }

}
