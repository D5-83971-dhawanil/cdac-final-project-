package com.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserSiginRequest;
import com.app.dto.UserSiginResponse;
import com.app.dto.UsersDto;
import com.app.pojos.Users;
import com.app.service.IRegisterService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins="*",allowedHeaders = "*")
public class UsersController {
	
	@Autowired
	private IRegisterService regService;

	@PostMapping("/register")
	public String registerUser(@RequestBody @Valid UsersDto userDto) {
		return regService.registerUser(userDto);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> validateUser(@RequestBody  UserSiginRequest userSiginRequest) {
		
		return ResponseEntity.ok(regService.validateUser(userSiginRequest.getMobileno(), userSiginRequest.getPassword()));
	}

	@GetMapping("/blog/{Buid}")
	public ResponseEntity<?> getBloggerUserdetails(@PathVariable long Buid){
		return ResponseEntity.ok(regService.getBloggerUserdetails(Buid));
	}
}
