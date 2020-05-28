package com.helpinghands.organization.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpinghands.exception.ResultException;
import com.helpinghands.organization.model.UserDonation;
import com.helpinghands.organization.model.UserDonationCategory;
import com.helpinghands.organization.model.UserDonationDetail;
import com.helpinghands.organization.repository.DonationRepository;
import com.helpinghands.response.Result;

@Service
public class DonationService {

	List<String> statusOfDonation = new ArrayList<>(Arrays.asList(new String[] { "Approving", "Declined", "Assigning",
			"In progress", "Dropped", "Completed", "Donated", "Canceled" }));

	@Autowired
	DonationRepository donationRepository;

	public Result<List<UserDonationDetail>> findAllDonationByUserId(int userId) {
		List<UserDonation> userDonations = donationRepository.findAllDonationByUserId(userId);
		if (userDonations.size() > 0) {
			List<UserDonationDetail> list = new ArrayList<>();
			userDonations.forEach(data -> {
				UserDonationDetail details = new UserDonationDetail();
				details.setOrgName(donationRepository.getOrganizationNameById(data.getOrgId()));
				details.setDetails(data);
				details.setCategoriesName(donationRepository.getDonationCategoryNames(data.getId()));
				details.setUserDetails(donationRepository.getUserDetailsByUserId(data.getUserId()).get(0));
				list.add(details);
			});
			return new Result<>(200, list);
		}
		throw new ResultException(new Result<>(404, "you have not donated to any organization yet!"));
	}

	public Result<List<UserDonationDetail>> findAllDonationByOrgId(int orgId) {
		List<UserDonation> userDonations = donationRepository.findAllDonationByOrgId(orgId);
		if (userDonations.size() > 0) {
			List<UserDonationDetail> list = new ArrayList<>();
			userDonations.forEach(data -> {
				UserDonationDetail details = new UserDonationDetail();
				details.setOrgName(donationRepository.getOrganizationNameById(data.getOrgId()));
				details.setDetails(data);
				details.setCategoriesName(donationRepository.getDonationCategoryNames(data.getId()));
				details.setUserDetails(donationRepository.getUserDetailsByUserId(data.getUserId()).get(0));
				list.add(details);
			});
			return new Result<>(200, list);
		}
		throw new ResultException(new Result<>(404, "their is no donation for your organization"));
	}

	public Result<UserDonationDetail> addUserDonationToOrganization(UserDonationDetail model) throws Exception {
		model.setStatus("Approving");
		model.setDonated(false);
		model.setDonationDate(new Date(System.currentTimeMillis()));
		model.setUserDetails(donationRepository.getUserDetailsByUserId(model.getUserId()).get(0));
		int donationId = donationRepository.addUserDonationToOrganization(model);
		if (donationId > 0) {
			List<UserDonationCategory> listOfCategories = model.getCategories();
			listOfCategories.forEach(data -> {
				data.setDonationId(donationId);
			});
			if (donationRepository.addUserDonationCategory(listOfCategories) > 0) {
				return new Result<>(200, model);
			}
			throw new ResultException(new Result<>(404, "Error! one or more categories were not added"));
		}
		throw new ResultException(new Result<>(404, "Error! something went wrong while donating! please try again"));
	}

	public Result<String> updateDonationStatus(int id, String status) {
		status = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
		if (statusOfDonation.contains(status)) {
			if (donationRepository.updateDonationStatus(id, status) > 0) {
				return new Result<>(201, "successfully updated the status");
			}
			throw new ResultException(new Result<>(404, "Error in updating status! id is-invalid. please try again"));
		}
		throw new ResultException(new Result<>(404, "Error in updating status! status is-invalid. please try again"));
	}

	public Result<String> getDonationStatusById(int id) {
		List<String> list = donationRepository.getDonationStatusById(id);
		if (list.size() > 0) {
			return new Result<>(200, list.get(0));
		}
		throw new ResultException(new Result<>(404, "Error! id is invalid. please try again"));		
	}
}