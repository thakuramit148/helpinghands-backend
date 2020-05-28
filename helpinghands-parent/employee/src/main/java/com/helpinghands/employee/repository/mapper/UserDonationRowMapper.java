package com.helpinghands.employee.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.helpinghands.employee.model.UserDonation;

public class UserDonationRowMapper implements RowMapper<UserDonation> {

	@Override
	public UserDonation mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserDonation donation = new UserDonation();
		donation.setId(rs.getInt("id"));
		donation.setUserId(rs.getInt("user_id"));
		donation.setOrgId(rs.getInt("org_id"));
		donation.setDropType(rs.getString("drop_type"));
		donation.setDonationDate(rs.getDate("donation_date"));
		donation.setDonationReceivedDate(rs.getDate("donation_received_date"));
		donation.setStatus(rs.getString("status"));
		donation.setDescription(rs.getString("description"));
		donation.setDonated(rs.getBoolean("is_donated"));
		return donation;
	}

}
