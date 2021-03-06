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
public class VolunteerPickup {
	@QueryHelperPrimaryKey
	private int id;
	@QueryHelperColumnName(name="vol_id")
	@Min(1)
	private int volId;
	@QueryHelperColumnName(name="donation_id")
	@Min(1)
	private int donationId;
	@NotNull
	private String status;

}
