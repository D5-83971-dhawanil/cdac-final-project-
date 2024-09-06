package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.pojos.Users;
import com.app.service.IVendorService;

//@CrossOrigin(origins ="http://localhost:3000")
@RestController // @Controller at the class level + @ResponseBody : on ret types of all req
				// handling methods
@CrossOrigin
public class ProfileController {
	@Autowired
	private IVendorService service;
	
	
	@GetMapping("/profile/{userId}")
	public ResponseEntity<?> viewProfile(@PathVariable long userId)
	{
		return ResponseEntity.ok(service.showProfile(userId));
	}

	@PutMapping("/profile/{uid}")
	public ResponseEntity<?> editProfile(@RequestBody Users vendor)
	{
		return ResponseEntity.ok(service.editProfile(vendor));
	}
}
