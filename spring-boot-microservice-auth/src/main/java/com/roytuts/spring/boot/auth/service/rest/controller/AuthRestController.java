package com.roytuts.spring.boot.auth.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.roytuts.spring.boot.auth.service.util.JwtUtil;

@RestController
public class AuthRestController {

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/auth/login")
	public ResponseEntity<TokenDTO> login(@RequestBody String userName) {
		String token = jwtUtil.generateToken(userName);
		return new ResponseEntity<TokenDTO>(new TokenDTO(token), HttpStatus.OK);
	}

	@PostMapping("/auth/register")
	public ResponseEntity<String> register(@RequestBody String userName) {
		return new ResponseEntity<String>("Registered", HttpStatus.OK);
	}

}
