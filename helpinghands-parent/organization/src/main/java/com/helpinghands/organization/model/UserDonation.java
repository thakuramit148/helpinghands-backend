package com.helpinghands.organization.model;

import java.sql.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.helpinghands.queryhelper.annotations.QueryHelperColumnName;
import com.helpinghands.queryhelper.annotations.QueryHelperNullValue;
import com.helpinghands.queryhelper.annotations.QueryHelperPrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDonation {
	@QueryHelperPrimaryKey
	private int id;
	@QueryHelperColumnName(name = "user_id")
	@Min(1)
	private int userId;
	@Min(1)
	@QueryHelperColumnName(name = "org_id")
	private int orgId;
	@NotNull
	@QueryHelperColumnName(name = "drop_type")
	private String dropType;
	@QueryHelperColumnName(name = "donation_date")
	private Date donationDate;
	@QueryHelperColumnName(name = "donation_received_date")
	@QueryHelperNullValue
	private Date donationReceivedDate;
	private String status;
	@NotNull
	private String description;
	@QueryHelperColumnName(name = "is_donated")
	private Boolean donated;

	public UserDonation getDetails(UserDonationDetail model) {
		return new UserDonation(model.getId(), model.getUserId(), model.getOrgId(), model.getDropType(),
				model.getDonationDate(), model.getDonationReceivedDate(), model.getStatus(), model.getDescription(),
				model.getDonated());
	}
}
