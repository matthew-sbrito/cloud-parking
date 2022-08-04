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
//        Role roleAdmin = new Role();
//
//        roleAdmin.setAuthority("USER");
//        roleAdmin = roleService.saveDomain(roleAdmin);
//
//        ApplicationUser user = new ApplicationUser();
//        user.setEnabled(true);
//        user.setEmail("teste@teste.com");
//        user.setFullName("Matheus Brito");
//        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
//        user.setUsername("matheus");
//        user.setRoles(Collections.singletonList(roleAdmin));
//
//        userDetailsServiceImpl.saveDomain(user);
//
//        log.info("Save user {}", user.toString());
    }

}
