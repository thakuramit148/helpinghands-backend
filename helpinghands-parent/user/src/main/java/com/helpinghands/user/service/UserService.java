package com.helpinghands.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.helpinghands.exception.ResultException;
import com.helpinghands.user.model.OrganizationVolunteer;
import com.helpinghands.user.model.User;
import com.helpinghands.user.model.UserWithNewPassword;
import com.helpinghands.user.model.UserWithPassword;
import com.helpinghands.user.repository.UserRepository;
import com.helpinghands.response.Result;
import com.helpinghands.response.Result.ComplainSystemError;

@Service
public class UserService {

	@Autowired
	@Qualifier("userRepo")
	UserRepository userRepository;

	public Result<List<User>> findAllUsers() {
		List<User> list = userRepository.findAllUsers();
		return new Result<>(200, list);
	}

	public Result<User> findUsersById(int id) throws Exception {
		List<User> list = userRepository.findUserById(id);
		if (list.size() > 0) {
			return new Result<>(200, list.get(0));
		}
		throw new ResultException(new Result<>(404, "no user's found, please try again!", new ArrayList<>(Arrays.asList(
				new ComplainSystemError((id + "").hashCode(), "user with given id('" + id + "') does not exists")))));
	}

	public Result<User> addUser(UserWithPassword userWithPassword) throws Exception {
		int id = userRepository.addUser(userWithPassword);
		userWithPassword.setId(id);
		if (id > 0) {
			return new Result<>(201, new User(userWithPassword));
		}
		throw new ResultException(new Result<>(400, "Error!, please try again!", new ArrayList<>(
				Arrays.asList(new ComplainSystemError(userWithPassword.hashCode(), "unable to add the given user")))));
	}

	public Result<User> updateUser(int id, User user) throws Exception {
		if (userRepository.updateUser(id, user)) {
			return new Result<>(200, user);
		}
		throw new ResultException(
				new Result<>(400, "Unable to update the given user, please try again!", new ArrayList<>(Arrays.asList(
						new ComplainSystemError(user.hashCode(), "given userId('" + id + "') does not exists ")))));
	}

	public Result<String> updateActiveStatus(int id, boolean status) throws Exception {
		if (userRepository.updateActiveStatus(id, status)) {
			return new Result<>(200, "status of given id(" + id + ") has been succefully updated to '" + status + "'");
		}
		throw new ResultException(new Result<>(400, "Unable to update the given user, please try again!",
				new ArrayList<>(Arrays.asList(new ComplainSystemError((id + "").hashCode(),
						"given userId('" + id + "') does not exists ")))));
	}

	public Result<String> updatePassword(UserWithNewPassword userWithNewPassword) throws Exception {
		int result = userRepository.updatePassword(userWithNewPassword);
		if (result > 0) {
			return new Result<>(200, "password has been succefully updated");
		} else if (result == -1) {
			throw new ResultException(new Result<>(400, "old password does not match!"));
		} else {
			throw new ResultException(new Result<>(400, "Error!, please try again!", new ArrayList<>(Arrays
					.asList(new ComplainSystemError("updatePassword".hashCode(), "unable to update the password!")))));
		}
	}

	public Result<List<OrganizationVolunteer>> getOrganizationVolunteer(int userId) {
		List<OrganizationVolunteer> list = userRepository.getOrganizationVolunteer(userId);
		if (list.size() > 0) {
			return new Result<>(200, list);
		}
		throw new ResultException(new Result<>(404, "you have not volunteered for any organization yet!"));
	}

}
