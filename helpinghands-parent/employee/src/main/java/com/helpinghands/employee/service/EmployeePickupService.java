package com.helpinghands.employee.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.helpinghands.employee.model.EmployeePickup;
import com.helpinghands.employee.model.UserDonation;
import com.helpinghands.employee.model.UserDonationDetail;
import com.helpinghands.employee.repository.EmployeePickupRepository;
import com.helpinghands.exception.ResultException;
import com.helpinghands.response.Result;

@Service
public class EmployeePickupService {

	List<String> statusOfDonation = new ArrayList<>(Arrays.asList(new String[] { "Approving", "Declined", "Assigning",
			"In progress", "Dropped", "Completed", "Donated", "Canceled" }));

	@Autowired
	@Qualifier("employeePickupRepo")
	EmployeePickupRepository employeePickupRepository;

	public Result<List<UserDonationDetail>> getAllPickupsByEmployeeId(int empId) {
		List<UserDonation> userDonations = employeePickupRepository.getAllPickupsByEmployeeId(empId);
		if (userDonations.size() > 0) {
			List<UserDonationDetail> list = new ArrayList<>();
			userDonations.forEach(data -> {
				UserDonationDetail details = new UserDonationDetail();
				details.setPickupId(employeePickupRepository.getPickupId(empId, data.getId()).get(0));
				details.setOrgName(employeePickupRepository.getOrganizationNameById(data.getOrgId()));
				details.setDetails(data);
				details.setCategoriesName(employeePickupRepository.getDonationCategoryNames(data.getId()));
				details.setUserDetails(employeePickupRepository.getUserDetailsByUserId(data.getUserId()).get(0));
				list.add(details);
			});
			return new Result<>(200, list);
		}
		throw new ResultException(new Result<>(404, "you have not taken any donation request yet!"));
	}

	public Result<EmployeePickup> addEmployeePickup(EmployeePickup employee) throws Exception {
		employee.setStatus("In progress");
		int id = employeePickupRepository.addEmployeePickup(employee);
		if (id > 0) {
			if (employeePickupRepository.updateDonationStatus(employee.getDonationId(), employee.getStatus()) > 0) {
				employee.setId(id);
				return new Result<>(200, employee);
			}
			throw new ResultException(new Result<>(404,
					"Error! something went wrong while updating the donation status! please try again"));
		}
		throw new ResultException(new Result<>(404, "Error! something went wrong while assigning! please try again"));
	}

	public Result<String> updateEmployeePickupStatus(int empPickupId, String status, int dontaionId) {
		status = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
		if (statusOfDonation.contains(status) && (status.equals("Dropped") || status.equals("Completed"))) {
			if (employeePickupRepository.updateEmployeePickupStatus(empPickupId, status) > 0) {
				if (employeePickupRepository.updateDonationStatus(dontaionId, status) > 0) {
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
