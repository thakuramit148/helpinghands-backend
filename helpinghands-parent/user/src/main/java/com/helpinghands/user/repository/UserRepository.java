package com.helpinghands.user.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.helpinghands.user.model.OrganizationVolunteer;
import com.helpinghands.user.model.User;
import com.helpinghands.user.model.UserWithNewPassword;
import com.helpinghands.user.model.UserWithPassword;

@Repository
public interface UserRepository {
	public List<User> findAllUsers();

	public List<User> findUserById(int id) throws Exception;

	public int addUser(UserWithPassword user) throws Exception;

	public boolean updateUser(int id, User user) throws Exception;

	public boolean updateActiveStatus(int id, boolean isActive) throws Exception;

	public int updatePassword(UserWithNewPassword userWithNewPassword) throws Exception;

	public List<OrganizationVolunteer> getOrganizationVolunteer(int userId);

}
