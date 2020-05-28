package com.helpinghands.employee.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.helpinghands.exception.ResultException;
import com.helpinghands.employee.model.Employee;
import com.helpinghands.employee.model.EmployeeWithNewPassword;
import com.helpinghands.employee.model.EmployeeWithPassword;
import com.helpinghands.employee.repository.EmployeeRepository;
import com.helpinghands.response.Result;
import com.helpinghands.response.Result.ComplainSystemError;

@Service
public class EmployeeService {

	@Autowired
	@Qualifier("employeeRepo")
	EmployeeRepository employeeRepository;

	public Result<List<Employee>> findAllEmployeesByOrgId(int orgId) {
		List<Employee> list = employeeRepository.findAllEmployeesByOrgId(orgId);
		if (list.size() > 0) {
			return new Result<>(200, list);
		}
		throw new ResultException(
				new Result<>(404, "no employee's found for org_id('" + orgId + "')"));
	}

	public Result<Employee> findEmployeesById(int id) throws Exception {
		List<Employee> list = employeeRepository.findEmployeeById(id);
		if (list.size() > 0) {
			return new Result<>(200, list.get(0));
		}
		throw new ResultException(new Result<>(404, "no employee's found, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError((id + "").hashCode(),
						"employee with given id('" + id + "') does not exists")))));
	}

	public Result<Employee> addEmployee(EmployeeWithPassword employeeWithPassword) throws Exception {
		int id = employeeRepository.addEmployee(employeeWithPassword);
		employeeWithPassword.setId(id);
		if (id > 0) {
			return new Result<>(201, new Employee(employeeWithPassword));
		}
		throw new ResultException(new Result<>(400, "Error!, please try again!", new ArrayList<>(Arrays.asList(
				new ComplainSystemError(employeeWithPassword.hashCode(), "unable to add the given employee")))));
	}

	public Result<Employee> updateEmployee(int id, Employee employee) throws Exception {
		if (employeeRepository.updateEmployee(id, employee)) {
			return new Result<>(200, employee);
		}
		throw new ResultException(new Result<>(400, "Unable to update the given employee, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError(employee.hashCode(),
						"given employeeId('" + id + "') does not exists ")))));
	}

	public Result<String> updateActiveStatus(int id, boolean status) throws Exception {
		if (employeeRepository.updateActiveStatus(id, status)) {
			return new Result<>(200, "status of given id(" + id + ") has been succefully updated to '" + status + "'");
		}
		throw new ResultException(new Result<>(400, "Unable to update the given employee, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError((id + "").hashCode(),
						"given employeeId('" + id + "') does not exists ")))));
	}

	public Result<String> updatePassword(EmployeeWithNewPassword employeeWithNewPassword) throws Exception {
		int result = employeeRepository.updatePassword(employeeWithNewPassword);
		if (result > 0) {
			return new Result<>(200, "password has been succefully updated");
		} else if (result == -1) {
			throw new ResultException(new Result<>(400, "old password does not match!"));
		} else {
			throw new ResultException(new Result<>(400, "Error!, please try again!", new ArrayList<>(Arrays
					.asList(new ComplainSystemError("updatePassword".hashCode(), "unable to update the password!")))));
		}
	}
	
	public Result<Integer> getOrganizationIdForEmployee(int id) {
		List<Integer> list = employeeRepository.getOrganizationIdForEmployee(id);
		if (list.size() > 0) {
			return new Result<>(200, list.get(0));
		}
		throw new ResultException(new Result<>(404, "Error! No orgid found, employee id is inavlid, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError((id + "").hashCode(),
						"employee with given id('" + id + "') does not exists")))));
	}
}
