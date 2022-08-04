package com.techsoft.parking.authentication.controller;

import com.techsoft.parking.authentication.domain.ApplicationUser;
import com.techsoft.parking.authentication.form.UserForm;
import com.techsoft.parking.authentication.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserDetailsServiceImpl userDetailsServiceImpl;


	public AuthController(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	@PostMapping("/create")
	public ResponseEntity<ApplicationUser> create(UserForm userForm) {
        ApplicationUser user = userDetailsServiceImpl.saveDto(userForm);

//        URI uri = URI.create(String.format("/user/%s", user.getId().toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

}
