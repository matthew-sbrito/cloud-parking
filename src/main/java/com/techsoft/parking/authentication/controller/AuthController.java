package com.techsoft.parking.authentication.controller;

import com.techsoft.parking.authentication.domain.ApplicationUser;
import com.techsoft.parking.authentication.dto.form.ApplicationUserCreateDTO;
import com.techsoft.parking.authentication.dto.response.ApplicationUserDTO;
import com.techsoft.parking.authentication.service.UserDetailsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "Create user controller")
public class AuthController {

	private final UserDetailsServiceImpl userDetailsServiceImpl;

	public AuthController(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	@PostMapping
	@ApiOperation("Create user")
	public ResponseEntity<ApplicationUserDTO> create(@RequestBody ApplicationUserCreateDTO applicationUserCreateDTO) {
        ApplicationUser user = userDetailsServiceImpl.saveDto(applicationUserCreateDTO);

//        URI uri = URI.create(String.format("/user/%s", user.getId().toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApplicationUserDTO(user));
	}
}
