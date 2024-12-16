package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@PostMapping("/new")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return userService.addUser(userInfo);
	}

	@GetMapping("/welcome")
	public String message() {

		return "Hello My World";
	}

	@GetMapping("/admin")
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminMessage() {
		return "Hello My World for ADMIN";
	}

	@GetMapping("/user")
//	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userMessage() {
		return "Hello My World for USER";
	}

	@GetMapping("/other")
	public String otherMessage() {
		return "Hello My World for all";
	}

}
