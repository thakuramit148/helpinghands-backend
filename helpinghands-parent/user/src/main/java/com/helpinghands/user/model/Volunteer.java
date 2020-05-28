package com.helpinghands.user.model;

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
public class Volunteer {
	@QueryHelperPrimaryKey
	private int id;
	@QueryHelperColumnName(name="org_id")
	@Min(1)
	private int orgId;
	@QueryHelperColumnName(name="user_id")
	@Min(1)
	private int userId;
	@NotNull
	@QueryHelperColumnName(name="is_approved")
	private Boolean approved;

}
