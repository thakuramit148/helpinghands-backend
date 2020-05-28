package com.helpinghands.employee.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.helpinghands.queryhelper.annotations.QueryHelperColumnName;
import com.helpinghands.queryhelper.annotations.QueryHelperPrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@QueryHelperPrimaryKey
	private int id;
	@NotNull
	private String username;
	@QueryHelperColumnName(name = "full_name")
	@NotNull
	private String fullname;
	@NotNull
	private String email;
	@NotNull
	private String phone;
	@NotNull
	private String address;
	@NotNull
	@QueryHelperColumnName(name = "is_active")
	private Boolean active;
	@Min(1)
	@QueryHelperColumnName(name = "org_id")
	private int orgId;

	public Employee(EmployeeWithPassword employeeWithPassword) {
		super();
		id = employeeWithPassword.getId();
		username = employeeWithPassword.getUsername();
		fullname = employeeWithPassword.getFullname();
		email = employeeWithPassword.getEmail();
		phone = employeeWithPassword.getPhone();
		address = employeeWithPassword.getAddress();
		active = employeeWithPassword.getActive();
		orgId = employeeWithPassword.getOrgId();
	}

}
