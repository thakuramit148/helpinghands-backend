package com.helpinghands.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.helpinghands.exception.ResultException;
import com.helpinghands.response.Result;
import com.helpinghands.user.model.OrganizationVolunteer;
import com.helpinghands.user.model.UserDonation;
import com.helpinghands.user.model.UserDonationDetail;
import com.helpinghands.user.model.Volunteer;
import com.helpinghands.user.model.VolunteerPickup;
import com.helpinghands.user.repository.VolunteerRepository;

@Service
public class VolunteerService {

	List<String> statusOfDonation = new ArrayList<>(Arrays.asList(new String[] { "Approving", "Declined", "Assigning",
			"In progress", "Dropped", "Completed", "Donated", "Canceled" }));

	@Autowired
	@Qualifier("volunteerRepo")
	VolunteerRepository volunteerRepository;

	public Result<List<UserDonationDetail>> getAllPickupsByVolunteerId(int volId) {
		List<UserDonation> userDonations = volunteerRepository.getAllPickupsByVolunteerId(volId);
		if (userDonations.size() > 0) {
			List<UserDonationDetail> list = new ArrayList<>();
			userDonations.parallelStream().forEach(data -> {
				UserDonationDetail details = new UserDonationDetail();
				details.setPickupId(volunteerRepository.getPickupId(volId, data.getId()).get(0));
				details.setOrgName(volunteerRepository.getOrganizationNameById(data.getOrgId()));
				details.setDetails(data);
				details.setCategoriesName(volunteerRepository.getDonationCategoryNames(data.getId()));
				details.setUserDetails(volunteerRepository.getUserDetailsByUserId(data.getUserId()).get(0));
				list.add(details);
			});
			return new Result<>(200, list);
		}
		throw new ResultException(new Result<>(404, "you have not taken any donation request yet!"));
	}

	public Result<Volunteer> addVolunteer(Volunteer volunteer) throws Exception {
		if (volunteerRepository.checkVolunteer(volunteer).size() == 0) {
			volunteer.setApproved(true);
			int id = volunteerRepository.addVolunteer(volunteer);
			if (id > 0) {
				volunteer.setId(id);
				return new Result<>(201, volunteer);
			}
			throw new ResultException(
					new Result<>(404, "Error! something went wrong while adding the volunteer! please try again"));
		}
		throw new ResultException(
				new Result<>(404, "You have already volunteered for this organization"));
	}

	public Result<VolunteerPickup> addVolunteerPickup(VolunteerPickup volunteer) throws Exception {
		volunteer.setStatus("In progress");
		int id = volunteerRepository.addVolunteerPickup(volunteer);
		if (id > 0) {
			if (volunteerRepository.updateDonationStatus(volunteer.getDonationId(), volunteer.getStatus()) > 0) {
				volunteer.setId(id);
				return new Result<>(200, volunteer);
			}
			throw new ResultException(new Result<>(404,
					"Error! something went wrong while updating the donation status! please try again"));
		}
		throw new ResultException(new Result<>(404, "Error! something went wrong while assigning! please try again"));
	}

	public Result<String> updateVolunteerPickupStatus(int empPickupId, String status, int dontaionId) {
		status = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
		if (statusOfDonation.contains(status) && (status.equals("Dropped") || status.equals("Completed"))) {
			if (volunteerRepository.updateVolunteerPickupStatus(empPickupId, status) > 0) {
				if (volunteerRepository.updateDonationStatus(dontaionId, status) > 0) {
					return new Result<>(201, "successfully updated the status");
				}
				throw new ResultException(
						new Result<>(404, "Error in updating status! donation id is invalid. please try again"));
			}
			throw new ResultException(
					new Result<>(404, "Error in updating status! pickup id is invalid. please try again"));
		}
		throw new ResultException(new Result<>(404, "Error in updating status! status is invalid. please try again"));
	}	
}
