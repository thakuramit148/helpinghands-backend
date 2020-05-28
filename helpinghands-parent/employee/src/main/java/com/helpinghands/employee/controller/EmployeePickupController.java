package com.helpinghands.employee.controller;

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

import com.helpinghands.employee.model.EmployeePickup;
import com.helpinghands.employee.model.UserDonationDetail;
import com.helpinghands.employee.service.EmployeePickupService;
import com.helpinghands.response.Result;

@RestController()
@RequestMapping(path = "/employee/pickup", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeePickupController {

	@Autowired
	EmployeePickupService employeePickupService;

	@GetMapping("/{id}")
	public ResponseEntity<Result<List<UserDonationDetail>>> getAllPickupsByEmployeeId(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int empId) {
		Result<List<UserDonationDetail>> result = employeePickupService.getAllPickupsByEmployeeId(empId);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/")
	public ResponseEntity<Result<EmployeePickup>> addEmployeePickup(
			@RequestBody(required = true) @Valid EmployeePickup employeePickup) throws Exception {
		Result<EmployeePickup> result = employeePickupService.addEmployeePickup(employeePickup);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/{id}/{status}/{donationId}")
	public ResponseEntity<Result<String>> updateEmployeePickupStatus(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id, @PathVariable("status") String status,
			@PathVariable("donationId") @Valid @Pattern(regexp = "[0-9]*") int donationId) {
		Result<String> result = employeePickupService.updateEmployeePickupStatus(id, status, donationId);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

}
