package com.helpinghands.organization.model;

import javax.validation.constraints.NotNull;

import com.helpinghands.queryhelper.annotations.QueryHelperColumnName;
import com.helpinghands.queryhelper.annotations.QueryHelperPrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationAddress {
	@QueryHelperPrimaryKey
	private int id;
	@QueryHelperColumnName(name="org_id")
	private int orgId;
	@NotNull
	private String state;
	@NotNull
	private String city;
	@NotNull
	private String area;
	@NotNull
	@QueryHelperColumnName(name="is_active")
	private Boolean active;
}
