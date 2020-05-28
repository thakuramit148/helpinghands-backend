package com.helpinghands.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.helpinghands.exception.ResultException;
import com.helpinghands.admin.model.admin.Admin;
import com.helpinghands.admin.model.admin.AdminWithNewPassword;
import com.helpinghands.admin.model.admin.AdminWithPassword;
import com.helpinghands.admin.repository.AdminRepository;
import com.helpinghands.response.Result;
import com.helpinghands.response.Result.ComplainSystemError;

@Service
public class AdminService {

	@Autowired
	@Qualifier("adminRepo")
	AdminRepository adminRepository;

	public Result<List<Admin>> findAllAdmins() {
		List<Admin> list = adminRepository.findAllAdmins();
		return new Result<>(200, list);
	}

	public Result<Admin> findAdminsById(int id) throws Exception {
		List<Admin> list = adminRepository.findAdminById(id);
		if (list.size() > 0) {
			return new Result<>(200, list.get(0));
		}
		throw new ResultException(new Result<>(404, "no admin's found, please try again!", new ArrayList<>(Arrays.asList(
				new ComplainSystemError((id + "").hashCode(), "admin with given id('" + id + "') does not exists")))));
	}

	public Result<Admin> addAdmin(AdminWithPassword adminWithPassword) throws Exception {
		int id = adminRepository.addAdmin(adminWithPassword);
		adminWithPassword.setId(id);
		if (id > 0) {
			return new Result<>(201, new Admin(adminWithPassword));
		}
		throw new ResultException(new Result<>(400, "Error!, please try again!", new ArrayList<>(
				Arrays.asList(new ComplainSystemError(adminWithPassword.hashCode(), "unable to add the given admin")))));
	}

	public Result<String> updatePassword(AdminWithNewPassword adminWithNewPassword) throws Exception {
		int result = adminRepository.updatePassword(adminWithNewPassword);
		if (result > 0) {
			return new Result<>(200, "password has been succefully updated");
		} else if (result == -1) {
			throw new ResultException(new Result<>(400, "old password does not match!"));
		} else {
			throw new ResultException(new Result<>(400, "Error!, please try again!", new ArrayList<>(Arrays
					.asList(new ComplainSystemError("updatePassword".hashCode(), "unable to update the password!")))));
		}
	}
}
