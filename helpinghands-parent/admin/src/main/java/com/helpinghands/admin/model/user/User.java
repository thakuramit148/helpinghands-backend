package com.helpinghands.admin.model.user;

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
public class User {
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
}
