package com.helpinghands.admin.controller;

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

import com.helpinghands.admin.client.UserClient;
import com.helpinghands.admin.model.admin.Admin;
import com.helpinghands.admin.model.admin.AdminWithNewPassword;
import com.helpinghands.admin.model.admin.AdminWithPassword;
import com.helpinghands.admin.model.user.User;
import com.helpinghands.admin.service.AdminService;
import com.helpinghands.response.Result;

@RestController()
@RequestMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	UserClient userClient;

	@GetMapping("/")
	public ResponseEntity<Result<List<Admin>>> getAllAdmins() {
		Result<List<Admin>> result = adminService.findAllAdmins();
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Result<Admin>> getAdminById(@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id)
			throws Exception {
		Result<Admin> result = adminService.findAdminsById(id);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/")
	public ResponseEntity<Result<Admin>> addAdmin(
			@RequestBody(required = true) @Valid AdminWithPassword adminWithPassword) throws Exception {
		Result<Admin> result = adminService.addAdmin(adminWithPassword);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/")
	public ResponseEntity<Result<String>> updatePassword(
			@RequestBody(required = true) @Valid AdminWithNewPassword adminWithNewPassword) throws Exception {
		Result<String> result = adminService.updatePassword(adminWithNewPassword);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@GetMapping("/users")
	public ResponseEntity<Result<List<User>>> getAllUsers() {
		Result<List<User>> result = userClient.getAllUsers();
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

}
