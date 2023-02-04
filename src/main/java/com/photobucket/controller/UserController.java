package com.photobucket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.photobucket.model.User;
import com.photobucket.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;

	@GetMapping("/homepage")
	public String welcomeMsg() {
		return "WELCOME TO PHOTO Bucket !";
	}
	
	@PostMapping("/saveUser")
	public ResponseEntity<User> saveUser(@RequestBody User user){
		return new ResponseEntity<User>(HttpStatus.OK);
		
		
	}
	
}
