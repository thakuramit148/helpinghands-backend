package com.helpinghands.organization.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.helpinghands.organization.model.UserDetails;
import com.helpinghands.organization.model.UserDonation;
import com.helpinghands.organization.model.UserDonationCategory;
import com.helpinghands.organization.model.UserDonationDetail;

@Repository
public interface DonationRepository {
	public List<UserDonation> findAllDonationByUserId(int userId);

	public List<UserDonation> findAllDonationByOrgId(int orgid);

	public List<String> getDonationCategoryNames(int donationId);

	public int addUserDonationToOrganization(UserDonationDetail model) throws Exception;

	public int addUserDonationCategory(List<UserDonationCategory> cateory) throws Exception;

	public List<UserDetails> getUserDetailsByUserId(int id);
	
	public int updateDonationStatus(int id, String status);

	public String getOrganizationNameById(int id);

	public List<String> getDonationStatusById(int id);
	
}
