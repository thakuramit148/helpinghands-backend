package com.helpinghands.employee.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.helpinghands.employee.model.EmployeePickup;
import com.helpinghands.employee.model.UserDetails;
import com.helpinghands.employee.model.UserDonation;

@Repository
public interface EmployeePickupRepository {
	List<UserDonation> getAllPickupsByEmployeeId(int id);

	int addEmployeePickup(EmployeePickup employee) throws Exception;

	int updateEmployeePickupStatus(int id, String status);

	int updateDonationStatus(int id, String status);

	List<String> getDonationCategoryNames(int donationId);

	List<UserDetails> getUserDetailsByUserId(int id);

	String getOrganizationNameById(int id);

	List<Integer> getPickupId(int empId, int donationId);
}
