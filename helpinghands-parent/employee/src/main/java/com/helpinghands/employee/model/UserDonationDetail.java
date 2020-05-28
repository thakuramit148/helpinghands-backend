package com.helpinghands.employee.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDonationDetail extends UserDonation {
	private String orgName;
	private int pickupId;
	private List<UserDonationCategory> categories;
	private List<String> categoriesName;
	private UserDetails userDetails;

	public void setDetails(UserDonation donation) {
		super.setId(donation.getId());
		super.setUserId(donation.getUserId());
		super.setOrgId(donation.getOrgId());
		super.setDescription(donation.getDescription());
		super.setStatus(donation.getStatus());
		super.setDropType(donation.getDropType());
		super.setDonationDate(donation.getDonationDate());
		super.setDonationReceivedDate(donation.getDonationReceivedDate());
		super.setDonated(donation.getDonated());
	}
}
