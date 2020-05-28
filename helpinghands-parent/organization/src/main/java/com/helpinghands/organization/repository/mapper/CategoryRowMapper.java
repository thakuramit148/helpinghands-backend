package com.helpinghands.organization.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.helpinghands.organization.model.OrganizationCategories;

public class CategoryRowMapper implements RowMapper<OrganizationCategories> {

	@Override
	public OrganizationCategories mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrganizationCategories org = new OrganizationCategories();
		org.setId(rs.getInt("id"));
		org.setName(rs.getString("name"));
		return org;
	}

}