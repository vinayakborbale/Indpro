package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LoginUserDto;
import com.example.dto.RegisterUserDto;
import com.example.entities.User;
import com.example.reponses.LoginResponse;
import com.example.service.AuthenticationService;
import com.example.service.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
		User registeredUser = authenticationService.signup(registerUserDto);
		return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
		LoginResponse loginResponse=null;
		User authenticatedUser = authenticationService.authenticate(loginUserDto);
		String jwtToken = jwtService.generateToken(authenticatedUser);
		
	 loginResponse = new LoginResponse().setToken(jwtToken)
				.setExpiresIn(jwtService.getExpirationTime());
	 loginResponse.setUserName(authenticatedUser.getUserName());

		return ResponseEntity.ok(loginResponse);
	}
}
