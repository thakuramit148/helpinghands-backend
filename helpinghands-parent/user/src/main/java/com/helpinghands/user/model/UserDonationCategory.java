package com.helpinghands.user.model;

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
public class UserDonationCategory {
	@QueryHelperPrimaryKey
	private int id;
	@QueryHelperColumnName(name = "donation_id")
	private int donationId;
	@QueryHelperColumnName(name = "donation_category_id")
	private int donationCategoryId;
}
