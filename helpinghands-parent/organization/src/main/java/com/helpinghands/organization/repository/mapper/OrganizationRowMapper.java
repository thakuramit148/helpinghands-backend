package com.helpinghands.organization.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.helpinghands.organization.model.Organization;

public class OrganizationRowMapper implements RowMapper<Organization> {

	@Override
	public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
		Organization organization = new Organization();
		organization.setId(rs.getInt("id"));
		organization.setUsername(rs.getString("username"));
		organization.setFullname(rs.getString("full_name"));
		organization.setEmail(rs.getString("email"));
		organization.setPhone(rs.getString("phone"));
		organization.setDescription(rs.getString("description"));
		organization.setActive(rs.getBoolean("is_active"));
		organization.setVerified(rs.getBoolean("is_verified"));
		return organization;
	}

}
