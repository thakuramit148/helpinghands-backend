package com.helpinghands.organization.model;

import javax.validation.constraints.NotNull;

import com.helpinghands.queryhelper.annotations.QueryHelperPrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationCategories {
	@QueryHelperPrimaryKey
	private int id;
	@NotNull
	private String name;
}
