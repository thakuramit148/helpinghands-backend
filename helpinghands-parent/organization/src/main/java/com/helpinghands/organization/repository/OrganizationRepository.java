package com.helpinghands.organization.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.helpinghands.organization.model.Organization;
import com.helpinghands.organization.model.OrganizationAddress;
import com.helpinghands.organization.model.OrganizationCategories;
import com.helpinghands.organization.model.OrganizationCategoryRefrence;
import com.helpinghands.organization.model.OrganizationSearchModel;
import com.helpinghands.organization.model.OrganizationWithNewPassword;
import com.helpinghands.organization.model.OrganizationWithPassword;

@Repository
public interface OrganizationRepository {
	public List<Organization> findAllOrganizations();

	public List<Organization> findOrganizationById(int id) throws Exception;

	public List<OrganizationAddress> findOrganizationAddressById(int id) throws Exception;

	public List<String> getOrganizationCategoriesById(int id) throws Exception;

	public List<Integer> getOrganizationDetailsByAreaOrCategory(OrganizationSearchModel model);

	public int getOrganizationRatingsById(int id) throws Exception;

	public int addOrganization(OrganizationWithPassword organization) throws Exception;

	public boolean updateOrganization(int id, Organization organization) throws Exception;

	public boolean updateActiveStatus(int id, boolean isActive) throws Exception;

	public boolean updateVerifiedStatus(int id, boolean isVerified) throws Exception;

	public int updatePassword(OrganizationWithNewPassword organizationWithNewPassword) throws Exception;

	public int addAddress(OrganizationAddress organizationAddress) throws Exception;

	public int updateAddress(OrganizationAddress organizationAddress) throws Exception;

	public boolean updateActiveStatusOfAddress(int id, boolean isActive) throws Exception;

	public List<OrganizationCategories> getAllCategories() throws Exception;

	public List<OrganizationCategories> getAllCategoriesOfOrg(int id);

	public int addCategory(OrganizationCategoryRefrence organizationAddress) throws Exception;
	
	List<Integer> checkVolunteer(int orgId, int userId);

}
