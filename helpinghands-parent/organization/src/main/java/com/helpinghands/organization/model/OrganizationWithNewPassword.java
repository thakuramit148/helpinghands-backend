package com.helpinghands.organization.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationWithNewPassword {
	@Min(1)
	private int id;
	@NotNull
	private String oldPassword;
	@NotNull
	private String newPassword;
}
