package com.helpinghands.organization.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.helpinghands.organization.model.OrganizationAddress;

public class OrganizationAddressRowMapper implements RowMapper<OrganizationAddress> {

	@Override
	public OrganizationAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrganizationAddress organization = new OrganizationAddress();
		organization.setId(rs.getInt("id"));
		organization.setOrgId(rs.getInt("org_id"));
		organization.setArea(rs.getString("area"));
		organization.setState(rs.getString("state"));
		organization.setCity(rs.getString("city"));
		organization.setActive(rs.getBoolean("is_active"));
		return organization;
	}

}
