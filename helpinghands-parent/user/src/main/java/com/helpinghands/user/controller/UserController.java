package com.helpinghands.user.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpinghands.response.Result;
import com.helpinghands.user.model.OrganizationVolunteer;
import com.helpinghands.user.model.User;
import com.helpinghands.user.model.UserWithNewPassword;
import com.helpinghands.user.model.UserWithPassword;
import com.helpinghands.user.service.UserService;

@RestController()
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/")
	public ResponseEntity<Result<List<User>>> getAllUsers() {
		Result<List<User>> result = userService.findAllUsers();
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Result<User>> getUserById(@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id)
			throws Exception {
		Result<User> result = userService.findUsersById(id);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/")
	public ResponseEntity<Result<User>> addUser(@RequestBody(required = true) @Valid UserWithPassword userWithPassword)
			throws Exception {
		Result<User> result = userService.addUser(userWithPassword);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Result<User>> updateUser(@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id,
			@RequestBody(required = true) @Valid User user) throws Exception {
		Result<User> result = userService.updateUser(id, user);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/{id}/{status}")
	public ResponseEntity<Result<String>> updateActiveStatus(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id,
			@PathVariable("status") @Valid @Pattern(regexp = "(true|false)") boolean status) throws Exception {
		Result<String> result = userService.updateActiveStatus(id, status);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/")
	public ResponseEntity<Result<String>> updatePassword(
			@RequestBody(required = true) @Valid UserWithNewPassword userWithNewPassword) throws Exception {
		Result<String> result = userService.updatePassword(userWithNewPassword);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}
	
	@GetMapping("/volunteered/{id}")
	public ResponseEntity<Result<List<OrganizationVolunteer>>> getOrganizationVolunteer(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int userId){
		Result<List<OrganizationVolunteer>> result = userService.getOrganizationVolunteer(userId);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	} 
}
