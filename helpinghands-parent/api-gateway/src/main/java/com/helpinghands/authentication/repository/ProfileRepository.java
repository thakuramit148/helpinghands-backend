package com.helpinghands.authentication.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.helpinghands.authentication.model.ProfileModel;

@Repository
public class ProfileRepository {

	private String tableName;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<ProfileModel> getAllProfiles(String tableName) {
		this.tableName = tableName;
		return jdbcTemplate.query("select * from " + tableName, new RowMapper<ProfileModel>() {
			@Override
			public ProfileModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProfileModel model = new ProfileModel();
				model.setId(rs.getInt("id"));
				model.setUsername(rs.getString("username"));
				model.setPassword(encoder.encode(rs.getString("password")));
				model.setRole(tableName);
				model.setActive(tableName.equalsIgnoreCase("admin") ? true : rs.getBoolean("is_active"));
				return model;
			}
		});
	}

	public ProfileModel getProfileByUsername(String username) {
		return jdbcTemplate.queryForObject("select * from " + tableName + " where username='" + username + "'",
				new RowMapper<ProfileModel>() {
					@Override
					public ProfileModel mapRow(ResultSet rs, int rowNum) throws SQLException {
						ProfileModel model = new ProfileModel();
						model.setId(rs.getInt("id"));
						model.setUsername(rs.getString("username"));
						model.setPassword(encoder.encode(rs.getString("password")));
						model.setRole(tableName);
						model.setActive(tableName.equalsIgnoreCase("admin") ? true : rs.getBoolean("is_active"));
						return model;
					}
				});
	}
}
