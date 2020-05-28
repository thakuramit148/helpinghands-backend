package com.helpinghands.organization.repository;

import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.helpinghands.organization.model.UserDetails;
import com.helpinghands.organization.model.UserDonation;
import com.helpinghands.organization.model.UserDonationCategory;
import com.helpinghands.organization.model.UserDonationDetail;
import com.helpinghands.organization.repository.mapper.UserDonationRowMapper;
import com.helpinghands.queryhelper.QueryGenerator;

@Repository
public class DonationRepositoryImpl implements DonationRepository {

	static String fields = "`id`, `user_id`, `org_id`, `drop_type`, `donation_date`, `donation_received_date`, `description`, `status`, `is_donated`";
	static String donationCategoryFields = "`id`, `donation_id`, `donation_category_id`";
	static String tableName = "user_donation";
	static String donationCategoryTableName = "user_donation_category";
	private QueryGenerator<UserDonation> queryGenerator = new QueryGenerator<UserDonation>();
	private QueryGenerator<UserDonationCategory> queryGeneratorCategory = new QueryGenerator<UserDonationCategory>();

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<UserDonation> findAllDonationByUserId(int userId) {
		String sql = "select " + fields + " from " + tableName + " where user_id = " + userId;
		return jdbcTemplate.query(sql, new UserDonationRowMapper());
	}

	@Override
	public List<UserDonation> findAllDonationByOrgId(int orgId) {
		String sql = "select " + fields + " from " + tableName + " where org_id = " + orgId;
		return jdbcTemplate.query(sql, new UserDonationRowMapper());
	}

	@Override
	public List<String> getDonationCategoryNames(int donationId) {
		String sql = "SELECT org_category.name FROM org_category, user_donation_category"
				+ " WHERE user_donation_category.donation_category_id = org_category.id "
				+ "and user_donation_category.donation_id = " + donationId;
		return jdbcTemplate.query(sql, (rs, next) -> rs.getString("name"));
	}

	@Override
	public int addUserDonationToOrganization(UserDonationDetail model) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				queryGenerator.generatePreparedStatementInsertQuery(tableName, new UserDonation().getDetails(model)),
				new BeanPropertySqlParameterSource((UserDonation) model), holder);
		return holder.getKey().intValue();
	}

	@Override
	public int addUserDonationCategory(List<UserDonationCategory> category) throws Exception {
		if (category.size() > 0) {
			String sql = queryGeneratorCategory.generatePreparedStatementInsertQuery(donationCategoryTableName,
					category.get(0));
			int size = 0;
			for (int i = 0; i < category.size(); i++) {
				size += jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(category.get(i)));
			}
			return size;
		}
		return -1;
	}

	@Override
	public List<UserDetails> getUserDetailsByUserId(int id) {
		String sql = "SELECT full_name,address,phone FROM user where id=" + id;
		return jdbcTemplate.query(sql, (rs, next) -> new UserDetails(rs.getString("full_name"),
				rs.getString("address").toString().replace("<>", ", ").replace("--", ", "), rs.getString("phone")));
	}

	@Override
	public int updateDonationStatus(int id, String status) {
		String sql = "";
		SqlParameterSource map = null;
		if(status.equals("Completed")){
			sql = "update " + tableName + " set status=:status, donation_received_date=:date where id=:id";
			map = new MapSqlParameterSource().addValue("id", id).addValue("status", "Completed").addValue("date",
					new Date(System.currentTimeMillis()));
		} else  {
			sql = "update " + tableName + " set status=:status where id=" + id;
			map = new MapSqlParameterSource().addValue("id", id).addValue("status", status);
		}
		return jdbcTemplate.update(sql, map);
	}
	
	@Override
	public String getOrganizationNameById(int id) {
		String sql = "SELECT full_name FROM organization where id=" + id;
		return jdbcTemplate.query(sql, (rs, next) -> rs.getString("full_name")).get(0);
	}
	
	@Override
	public List<String> getDonationStatusById(int id) {
		String sql = "SELECT status FROM user_donation where id=" + id;
		return jdbcTemplate.query(sql, (rs,next)->rs.getString("status"));		
	}
}
