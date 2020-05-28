package com.helpinghands.user.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.helpinghands.user.model.VolunteerPickup;
import com.helpinghands.user.model.UserDetails;
import com.helpinghands.user.model.UserDonation;
import com.helpinghands.user.model.Volunteer;

@Repository
public interface VolunteerRepository {
	List<UserDonation> getAllPickupsByVolunteerId(int id);

	int addVolunteer(Volunteer volunteer) throws Exception;

	List<Integer> checkVolunteer(Volunteer volunteer);

	int addVolunteerPickup(VolunteerPickup volunteer) throws Exception;

	int updateVolunteerPickupStatus(int id, String status);

	int updateDonationStatus(int id, String status);

	List<String> getDonationCategoryNames(int donationId);

	List<UserDetails> getUserDetailsByUserId(int id);

	String getOrganizationNameById(int id);

	List<Integer> getPickupId(int volId, int donationId);
}
