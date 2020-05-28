package com.helpinghands.admin.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.helpinghands.admin.model.admin.Admin;
import com.helpinghands.admin.model.admin.AdminWithNewPassword;
import com.helpinghands.admin.model.admin.AdminWithPassword;

@Repository
public interface AdminRepository {
	public List<Admin> findAllAdmins();

	public List<Admin> findAdminById(int id) throws Exception;

	public int addAdmin(AdminWithPassword admin) throws Exception;

	public int updatePassword(AdminWithNewPassword adminWithNewPassword) throws Exception;

}
