package com.helpinghands.employee.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.helpinghands.employee.model.Employee;

public class EmployeeRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setUsername(rs.getString("username"));
		employee.setFullname(rs.getString("full_name"));
		employee.setEmail(rs.getString("email"));
		employee.setPhone(rs.getString("phone"));
		employee.setAddress(rs.getString("address"));
		employee.setActive(rs.getBoolean("is_active"));
		employee.setOrgId(rs.getInt("org_id"));
		return employee;
	}

}
