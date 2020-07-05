package com.helpinghands.organization.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.helpinghands.exception.ResultException;
import com.helpinghands.organization.model.Organization;
import com.helpinghands.organization.model.OrganizationAddress;
import com.helpinghands.organization.model.OrganizationCategories;
import com.helpinghands.organization.model.OrganizationCategoryRefrence;
import com.helpinghands.organization.model.OrganizationDetail;
import com.helpinghands.organization.model.OrganizationSearchModel;
import com.helpinghands.organization.model.OrganizationWithNewPassword;
import com.helpinghands.organization.model.OrganizationWithPassword;
import com.helpinghands.organization.repository.OrganizationRepository;
import com.helpinghands.response.Result;
import com.helpinghands.response.Result.ComplainSystemError;

@Service
public class OrganizationService {

	@Autowired
	@Qualifier("organizationRepo")
	OrganizationRepository organizationRepository;

	public Result<List<Organization>> findAllOrganizations() {
		List<Organization> list = organizationRepository.findAllOrganizations();
		return new Result<>(200, list);
	}

	public Result<Organization> findOrganizationsById(int id) throws Exception {
		List<Organization> list = organizationRepository.findOrganizationById(id);
		if (list.size() > 0) {
			return new Result<>(200, list.get(0));
		}
		throw new ResultException(new Result<>(404, "no organization's found, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError((id + "").hashCode(),
						"organization with given id('" + id + "') does not exists")))));
	}

	public Result<OrganizationDetail> findOrganizationDetaislById(int id) throws Exception {
		List<Organization> list = organizationRepository.findOrganizationById(id);
		OrganizationDetail organizationDetail = new OrganizationDetail();
		if (list.size() > 0) {
			organizationDetail.setDetails(list.get(0));
			organizationDetail.setAddress(organizationRepository.findOrganizationAddressById(id));
			organizationDetail.setCategories(organizationRepository.getOrganizationCategoriesById(id));
			organizationDetail.setRatings(organizationRepository.getOrganizationRatingsById(id));
			return new Result<>(200, organizationDetail);
		}
		throw new ResultException(new Result<>(404, "no organization's found, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError((id + "").hashCode(),
						"organization with given id('" + id + "') does not exists")))));
	}

	public Result<List<OrganizationDetail>> getOrganizationDetailsByAreaOrCategory(OrganizationSearchModel model)
			throws Exception {
		List<Integer> listOfIds = organizationRepository.getOrganizationDetailsByAreaOrCategory(model);
		if (model.getCategoriesId().size() == 0 && model.getState().size() == 0) {
			return new Result<>(200, getList(listOfIds));
		} else {
			if (listOfIds.size() == 0) {
				return new Result<>(200, "No data found for your search details");
			} else if (listOfIds.size() > 0) {
				return new Result<>(200, getList(listOfIds));
			}
		}
		throw new ResultException(new Result<>(404, "something went wrong while searching, please try again!"));
	}
	
	public List<OrganizationDetail> getList(List<Integer> listOfIds){
		List<OrganizationDetail> list = new ArrayList<>();
		listOfIds.forEach(id -> {
			try {
				list.add(findOrganizationDetaislById(id).getData());
			} catch (Exception e) {
				throw new ResultException(new Result<>(404, e.getMessage()));
			}
		});
		return list;
	}

	public Result<Organization> addOrganization(OrganizationWithPassword organizationWithPassword) throws Exception {
		int id = organizationRepository.addOrganization(organizationWithPassword);
		organizationWithPassword.setId(id);
		if (id > 0) {
			return new Result<>(201, new Organization(organizationWithPassword));
		}
		throw new ResultException(new Result<>(400, "Error!, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError(organizationWithPassword.hashCode(),
						"unable to add the given organization")))));
	}

	public Result<Organization> updateOrganization(int id, Organization organization) throws Exception {
		if (organizationRepository.updateOrganization(id, organization)) {
			return new Result<>(200, organization);
		}
		throw new ResultException(new Result<>(400, "Unable to update the given organization, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError(organization.hashCode(),
						"given organizationId('" + id + "') does not exists ")))));
	}

	public Result<String> updateActiveStatus(int id, boolean status) throws Exception {
		if (organizationRepository.updateActiveStatus(id, status)) {
			return new Result<>(200, "status of given id(" + id + ") has been succefully updated to '" + status + "'");
		}
		throw new ResultException(new Result<>(400, "Unable to update the given organization, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError((id + "").hashCode(),
						"given organizationId('" + id + "') does not exists ")))));
	}

	public Result<String> updateVerifiedStatus(int id, boolean status) throws Exception {
		if (organizationRepository.updateVerifiedStatus(id, status)) {
			return new Result<>(200, "given id(" + id + ") has been succefully verified to '" + status + "'");
		}
		throw new ResultException(new Result<>(400, "Unable to update the given organization, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError((id + "").hashCode(),
						"given organizationId('" + id + "') does not exists ")))));
	}

	public Result<String> updatePassword(OrganizationWithNewPassword organizationWithNewPassword) throws Exception {
		int result = organizationRepository.updatePassword(organizationWithNewPassword);
		if (result > 0) {
			return new Result<>(200, "password has been succefully updated");
		} else if (result == -1) {
			throw new ResultException(new Result<>(400, "id does not exists or old password does not match!"));
		} else {
			throw new ResultException(new Result<>(400, "Error!, please try again!", new ArrayList<>(Arrays
					.asList(new ComplainSystemError("updatePassword".hashCode(), "unable to update the password!")))));
		}
	}

	public Result<OrganizationAddress> addAddress(OrganizationAddress organizationAddress) throws Exception {
		int id = organizationRepository.addAddress(organizationAddress);
		if (id > 0) {
			organizationAddress.setId(id);
			return new Result<>(201, organizationAddress);
		}
		throw new ResultException(new Result<>(400, "Error!, please try again!", new ArrayList<>(Arrays.asList(
				new ComplainSystemError(organizationAddress.hashCode(), "unable to add the given organization")))));
	}

	public Result<OrganizationAddress> updateAddress(OrganizationAddress organizationAddress) throws Exception {
		if (organizationRepository.updateAddress(organizationAddress) > 0) {
			return new Result<>(200, organizationAddress);
		}
		throw new ResultException(new Result<>(400, "Unable to update the given organization, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError(organizationAddress.hashCode(),
						"given organizationId('" + organizationAddress.getId() + "') does not exists ")))));
	}

	public Result<String> updateActiveStatusOfAddress(int id, boolean status) throws Exception {
		if (organizationRepository.updateActiveStatusOfAddress(id, status)) {
			return new Result<>(200, "status of given id(" + id + ") has been succefully updated to '" + status + "'");
		}
		throw new ResultException(new Result<>(400, "Unable to update the given organization, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError((id + "").hashCode(),
						"given organizationId('" + id + "') does not exists ")))));
	}

	public Result<List<OrganizationCategories>> getAllCategories() throws Exception {
		List<OrganizationCategories> list = organizationRepository.getAllCategories();
		if (list.size() > 0) {
			return new Result<>(200, list);
		}
		throw new ResultException(new Result<>(404, "no categories found, please try again!"));
	}

	public Result<List<OrganizationCategories>> getAllCategoriesOfOrg(int id) throws Exception {
		List<OrganizationCategories> list = organizationRepository.getAllCategoriesOfOrg(id);
		if (list.size() > 0) {
			return new Result<>(200, list);
		}
		throw new ResultException(new Result<>(404, "no categories found, please try again!"));
	}

	public Result<OrganizationCategoryRefrence> addCategoryRef(
			OrganizationCategoryRefrence organizationCategoryRefrence) throws Exception {
		int id = organizationRepository.addCategory(organizationCategoryRefrence);
		if (id > 0) {
			organizationCategoryRefrence.setId(id);
			return new Result<>(201, organizationCategoryRefrence);
		}
		throw new ResultException(new Result<>(400, "Error!, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError(organizationCategoryRefrence.hashCode(),
						"unable to add the given organization")))));
	}

}