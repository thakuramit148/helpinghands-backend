package com.helpinghands.employee.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.helpinghands.employee.model.Employee;
import com.helpinghands.employee.model.EmployeeWithNewPassword;
import com.helpinghands.employee.model.EmployeeWithPassword;

@Repository
public interface EmployeeRepository {
	public List<Employee> findAllEmployeesByOrgId(int orgId);

	public List<Employee> findEmployeeById(int id) throws Exception;

	public int addEmployee(EmployeeWithPassword employee) throws Exception;

	public boolean updateEmployee(int id, Employee employee) throws Exception;

	public boolean updateActiveStatus(int id, boolean isActive) throws Exception;

	public int updatePassword(EmployeeWithNewPassword employeeWithNewPassword) throws Exception;

	public List<Integer> getOrganizationIdForEmployee(int id);
}
