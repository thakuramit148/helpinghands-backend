package com.helpinghands.employee.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWithNewPassword {
	@Min(1)
	private int id;
	@NotNull
	private String oldPassword;
	@NotNull
	private String newPassword;
}
