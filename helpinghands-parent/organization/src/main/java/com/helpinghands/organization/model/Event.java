package com.helpinghands.organization.model;

import java.sql.Date;

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
public class Event {
	@QueryHelperPrimaryKey
	private int id;
	@NotNull
	private String name;
	@NotNull
	private Date start;
	@NotNull
	private Date end;
	@NotNull
	private String description;
	@QueryHelperColumnName(name="org_id")
	@Min(1)
	private int orgId;

}
