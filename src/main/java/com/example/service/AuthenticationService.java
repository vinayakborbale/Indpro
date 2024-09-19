package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.LoginUserDto;
import com.example.dto.RegisterUserDto;
import com.example.entities.User;
import com.example.repository.UserRepository;

@Service
public class AuthenticationService {

	private final ModelMapper mapper = new ModelMapper();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public User signup(RegisterUserDto regitUserDto) {
		
       User user = mapper.map(regitUserDto, User.class);
       user.setPassword(passwordEncoder.encode(regitUserDto.getPassword()));     
		return userRepository.save(user);
	}

	public User authenticate(LoginUserDto loginUserDto) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));
		return userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow();
	}

	public List<User> allUsers() {
		List<User> users = new ArrayList<>();

		userRepository.findAll().forEach(users::add);

		return users;
	}
}
