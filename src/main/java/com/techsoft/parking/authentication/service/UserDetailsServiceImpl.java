package com.techsoft.parking.authentication.service;

import com.techsoft.parking.authentication.domain.ApplicationUser;
import com.techsoft.parking.authentication.form.UserForm;
import com.techsoft.parking.authentication.repository.UserRepository;
import com.techsoft.parking.common.service.impl.AbstractCrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl extends AbstractCrudServiceImpl<ApplicationUser, Long, UserRepository, UserForm> implements UserDetailsService {

    @Autowired
    UserDetailsServiceImpl(UserRepository userRepository) {
        super(userRepository, ApplicationUser.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Searching in the DB the user by username '{}'", username);

        ApplicationUser applicationUser = repository.findByUsername(username);
        log.info("ApplicationUser found '{}'", applicationUser);

        if(applicationUser == null) {
            throw new UsernameNotFoundException(String.format("Application user '%s' not found!", username));
        }

        return applicationUser;
    }
}
