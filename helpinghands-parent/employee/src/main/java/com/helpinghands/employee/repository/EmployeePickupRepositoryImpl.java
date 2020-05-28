package com.helpinghands.employee.repository;

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

import com.helpinghands.employee.model.EmployeePickup;
import com.helpinghands.employee.model.UserDetails;
import com.helpinghands.employee.model.UserDonation;
import com.helpinghands.employee.repository.mapper.UserDonationRowMapper;
import com.helpinghands.queryhelper.QueryGenerator;

@Repository(value = "employeePickupRepo")
public class EmployeePickupRepositoryImpl implements EmployeePickupRepository {

	static String fields = "`id`, `user_id`, `org_id`, `drop_type`, `donation_date`, `donation_received_date`, `description`, `status`, `is_donated`";
	static String tableName = "user_donation";
	static String pickuptableName = "employee_pickup";
	private QueryGenerator<EmployeePickup> queryGenerator = new QueryGenerator<EmployeePickup>();

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<UserDonation> getAllPickupsByEmployeeId(int id) {
		String sql = "select " + fields + " from " + tableName
				+ " where id  in (SELECT donation_id FROM `employee_pickup` WHERE emp_id = " + id
				+ ") and drop_type='Pickup'";
		return jdbcTemplate.query(sql, new UserDonationRowMapper());
	}

	@Override
	public int addEmployeePickup(EmployeePickup employee) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(queryGenerator.generatePreparedStatementInsertQuery(pickuptableName, employee),
				new BeanPropertySqlParameterSource(employee), holder);
		return holder.getKey().intValue();
	}

	@Override
	public int updateEmployeePickupStatus(int id, String status) {
		String sql = "update " + pickuptableName + " set status=:status where id=" + id;
		return jdbcTemplate.update(sql, new MapSqlParameterSource().addValue("status", status));
	}

	@Override
	public int updateDonationStatus(int id, String status) {
		String sql = "";
		SqlParameterSource map = null;
		if (status.equals("Dropped") || status.equals("In progress")) {
			sql = "update " + tableName + " set status=:status where id=:id";
			map = new MapSqlParameterSource().addValue("id", id).addValue("status", status.equals("Dropped")?"Assigning":"In progress");
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
	public List<Integer> getPickupId(int empId, int donationId) {
		String sql = "SELECT id FROM employee_pickup where emp_id=" + empId + " and donation_id=" + donationId + " order by id desc limit 1";
		return jdbcTemplate.query(sql, (rs, next) -> rs.getInt("id"));
	}

}
