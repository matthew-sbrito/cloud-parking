package com.techsoft.parking.init;

import com.techsoft.parking.authentication.domain.Role;
import com.techsoft.parking.authentication.domain.ApplicationUser;
import com.techsoft.parking.authentication.service.RoleService;
import com.techsoft.parking.authentication.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class Initializer {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final RoleService roleService;


    public Initializer(UserDetailsServiceImpl userDetailsServiceImpl, RoleService roleService) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createUserWithPermission() {
        List<Role> roleList = roleService.list();

        Optional<Role> roleAdmin = roleList.stream().filter(role -> role.getAuthority().equals("ADMIN")).findFirst();
        Role role;

        if(roleAdmin.isPresent()) {
            role = roleAdmin.get();
        } else {
            role = new Role();
            role.setAuthority("ADMIN");
            role = roleService.saveDomain(role);
        }

        ApplicationUser user;

        try {
            user = (ApplicationUser) userDetailsServiceImpl.loadUserByUsername("matheus");
        } catch (Exception e) {
            user = null;
        }

        if(user == null) {
            user = new ApplicationUser();
            user.setEnabled(true);
            user.setEmail("matheusb@gmail.com");
            user.setFullName("Matheus Brito");
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            user.setUsername("matheus");
            user.setRoles(Collections.singletonList(role));

            userDetailsServiceImpl.saveDomain(user);

            log.info("Save user {}", user.toString());
        }

    }

}
