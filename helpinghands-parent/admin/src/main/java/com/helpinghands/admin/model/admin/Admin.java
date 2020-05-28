package com.helpinghands.admin.model.admin;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.helpinghands.queryhelper.annotations.QueryHelperPrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
	@QueryHelperPrimaryKey
	private int id;
	@NotNull
	private String username;
	
	public Admin(AdminWithPassword adminWithPassword) {
		super();
		id = adminWithPassword.getId();
		username = adminWithPassword.getUsername();
	}

}
