package com.helpinghands.user.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.helpinghands.user.model.VolunteerPickup;
import com.helpinghands.user.model.UserDetails;
import com.helpinghands.user.model.UserDonation;
import com.helpinghands.user.model.Volunteer;
import com.helpinghands.user.repository.mapper.UserDonationRowMapper;
import com.helpinghands.queryhelper.QueryGenerator;

@Repository(value = "volunteerRepo")
public class VolunteerRepositoryImpl implements VolunteerRepository {

	static String fields = "`id`, `user_id`, `org_id`, `drop_type`, `donation_date`, `donation_received_date`, `description`, `status`, `is_donated`";
	static String tableName = "user_donation";
	static String pickuptableName = "volunteer_pickup";
	static String volunteerTableName = "volunteer";
	private QueryGenerator<VolunteerPickup> queryGenerator = new QueryGenerator<VolunteerPickup>();
	private QueryGenerator<Volunteer> volunteerQueryGenerator = new QueryGenerator<Volunteer>();

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<UserDonation> getAllPickupsByVolunteerId(int id) {
		String sql = "select " + fields + " from " + tableName
				+ " where id  in (SELECT donation_id FROM `volunteer_pickup` WHERE vol_id = " + id
				+ ") and drop_type='Pickup'";
		return jdbcTemplate.query(sql, new UserDonationRowMapper());
	}

	@Override
	public int addVolunteer(Volunteer volunteer) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(volunteerQueryGenerator.generatePreparedStatementInsertQuery(volunteerTableName, volunteer),
				new BeanPropertySqlParameterSource(volunteer), holder);
		return holder.getKey().intValue();
	}

	@Override
	public List<Integer> checkVolunteer(Volunteer volunteer) {
		String sql = "select id from volunteer where org_id=" + volunteer.getOrgId() + " and user_id="
				+ volunteer.getUserId();
		return jdbcTemplate.query(sql, (rs, next) -> rs.getInt("id"));
	}

	@Override
	public int addVolunteerPickup(VolunteerPickup volunteer) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(queryGenerator.generatePreparedStatementInsertQuery(pickuptableName, volunteer),
				new BeanPropertySqlParameterSource(volunteer), holder);
		return holder.getKey().intValue();
	}

	@Override
	public int updateVolunteerPickupStatus(int id, String status) {
		String sql = "update " + pickuptableName + " set status=:status where id=" + id;
		return jdbcTemplate.update(sql, new MapSqlParameterSource().addValue("status", status));
	}

	@Override
	public int updateDonationStatus(int id, String status) {
		String sql = "";
		SqlParameterSource map = null;
		if (status.equals("Dropped") || status.equals("In progress")) {
			sql = "update " + tableName + " set status=:status where id=:id";
			map = new MapSqlParameterSource().addValue("id", id).addValue("status",
					status.equals("Dropped") ? "Assigning" : "In progress");
		} else if (status.equals("Completed")) {
			sql = "update " + tableName + " set status=:status, donation_received_date=:date where id=:id";
			map = new MapSqlParameterSource().addValue("id", id).addValue("status", "Completed").addValue("date",
					new Date(System.currentTimeMillis()));
		} else {
			return -1;
		}
		return jdbcTemplate.update(sql, map);
	}

	@Override
	public List<String> getDonationCategoryNames(int donationId) {
		String sql = "SELECT org_category.name FROM org_category, user_donation_category"
				+ " WHERE user_donation_category.donation_category_id = org_category.id "
				+ "and user_donation_category.donation_id = " + donationId;
		return jdbcTemplate.query(sql, (rs, next) -> rs.getString("name"));
	}

	@Override
	public List<UserDetails> getUserDetailsByUserId(int id) {
		String sql = "SELECT full_name,address,phone FROM user where id=" + id;
		return jdbcTemplate.query(sql, (rs, next) -> new UserDetails(rs.getString("full_name"),
				rs.getString("address").toString().replace("<>", ", ").replace("--", ", "), rs.getString("phone")));
	}

	@Override
	public String getOrganizationNameById(int id) {
		String sql = "SELECT full_name FROM organization where id=" + id;
		return jdbcTemplate.query(sql, (rs, next) -> rs.getString("full_name")).get(0);
	}

	@Override
	public List<Integer> getPickupId(int volId, int donationId) {
		String sql = "SELECT id FROM volunteer_pickup where vol_id=" + volId + " and donation_id=" + donationId
				+ " order by id desc limit 1";
		return jdbcTemplate.query(sql, (rs, next) -> rs.getInt("id"));
	}

}
