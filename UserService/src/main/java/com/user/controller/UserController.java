package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.UserInfo;
import com.user.service.UserService;

@RestController
@RequestMapping("/login")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/newUser")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return userService.addUser(userInfo);
	}

	@GetMapping("/welcome")
	public String message() {
		return "Hello My World";
	}

	@GetMapping("/admin")
	//@PreAuthorize("hasRole('ADMIN')")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String adminMessage() {
		return "Hello My World for ADMIN";
	}

	@GetMapping("/user")
	//@PreAuthorize("hasRole('USER')")  
	@PreAuthorize("hasAuthority('ADMIN')")
	public String userMessage() {
		return "Hello My World for USER";
	}

	@GetMapping("/other")
	public String otherMessage() {
		return "Hello My World for all";
	}

}
