package com.helpinghands.organization.model;

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
public class Organization {
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
	private String description;
	@NotNull
	@QueryHelperColumnName(name = "is_active")
	private Boolean active;
	@NotNull
	@QueryHelperColumnName(name = "is_verified")
	private Boolean verified;

	public Organization(OrganizationWithPassword organizationWithPassword) {
		super();
		id = organizationWithPassword.getId();
		username = organizationWithPassword.getUsername();
		fullname = organizationWithPassword.getFullname();
		email = organizationWithPassword.getEmail();
		phone = organizationWithPassword.getPhone();
		description = organizationWithPassword.getDescription();
		active = organizationWithPassword.getActive();
		verified = organizationWithPassword.getVerified();
	}

}
