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

import com.helpinghands.employee.model.Employee;
import com.helpinghands.employee.model.EmployeeWithNewPassword;
import com.helpinghands.employee.model.EmployeeWithPassword;
import com.helpinghands.employee.service.EmployeeService;
import com.helpinghands.response.Result;

@RestController()
@RequestMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/orgId/{orgId}")
	public ResponseEntity<Result<List<Employee>>> getAllEmployeesByOrgId(
			@PathVariable("orgId") @Valid @Pattern(regexp = "[0-9]*") int orgId) throws Exception {
		Result<List<Employee>> result = employeeService.findAllEmployeesByOrgId(orgId);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Result<Employee>> getEmployeeById(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
		Result<Employee> result = employeeService.findEmployeesById(id);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/")
	public ResponseEntity<Result<Employee>> addEmployee(
			@RequestBody(required = true) @Valid EmployeeWithPassword employeeWithPassword) throws Exception {
		Result<Employee> result = employeeService.addEmployee(employeeWithPassword);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Result<Employee>> updateEmployee(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id,
			@RequestBody(required = true) @Valid Employee employee) throws Exception {
		Result<Employee> result = employeeService.updateEmployee(id, employee);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/{id}/{status}")
	public ResponseEntity<Result<String>> updateActiveStatus(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id,
			@PathVariable("status") @Valid @Pattern(regexp = "(true|false)") boolean status) throws Exception {
		Result<String> result = employeeService.updateActiveStatus(id, status);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/")
	public ResponseEntity<Result<String>> updatePassword(
			@RequestBody(required = true) @Valid EmployeeWithNewPassword employeeWithNewPassword) throws Exception {
		Result<String> result = employeeService.updatePassword(employeeWithNewPassword);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}
	
	@GetMapping("/{id}/organizationId")
	public ResponseEntity<Result<Integer>> getOrganizationIdForEmployee(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
		Result<Integer> result = employeeService.getOrganizationIdForEmployee(id);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}
}
