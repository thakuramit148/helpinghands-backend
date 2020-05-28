package com.helpinghands.organization.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.helpinghands.organization.model.Organization;
import com.helpinghands.organization.model.OrganizationAddress;
import com.helpinghands.organization.model.OrganizationCategories;
import com.helpinghands.organization.model.OrganizationCategoryRefrence;
import com.helpinghands.organization.model.OrganizationSearchModel;
import com.helpinghands.organization.model.OrganizationWithNewPassword;
import com.helpinghands.organization.model.OrganizationWithPassword;
import com.helpinghands.organization.repository.mapper.CategoryRowMapper;
import com.helpinghands.organization.repository.mapper.OrganizationAddressRowMapper;
import com.helpinghands.organization.repository.mapper.OrganizationCategoryRowMapper;
import com.helpinghands.organization.repository.mapper.OrganizationRatingsRowMapper;
import com.helpinghands.organization.repository.mapper.OrganizationRowMapper;
import com.helpinghands.queryhelper.QueryGenerator;

@Repository(value = "organizationRepo")
public class OrganizationRepositoryImpl implements OrganizationRepository {

	static String fields = "`id`, `username`, `full_name`, `email`, `phone`, `description`, `is_active`, `is_verified`";
	static String addressFields = "`id`, `org_id`, `area`, `city`, `state`, `is_active`";
	static String tableName = "organization";
	static String addressTableName = "org_address";
	static String orgCatTableName = "org_category";
	static String orgCatRefTableName = "org_category_reference";
	private QueryGenerator<OrganizationWithPassword> queryGenerator = new QueryGenerator<OrganizationWithPassword>();
	private QueryGenerator<OrganizationAddress> queryGeneratorAddress = new QueryGenerator<OrganizationAddress>();
	private QueryGenerator<OrganizationCategoryRefrence> queryGeneratorCategory = new QueryGenerator<OrganizationCategoryRefrence>();

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Organization> findAllOrganizations() {
		String sql = "select " + fields + " from " + tableName;
		return jdbcTemplate.query(sql, new OrganizationRowMapper());
	}

	@Override
	public List<Organization> findOrganizationById(int id) throws Exception {
		String sql = "select " + fields + " from " + tableName + " where id=" + id;
		return jdbcTemplate.query(sql, new OrganizationRowMapper());
	}

	@Override
	public List<OrganizationAddress> findOrganizationAddressById(int id) throws Exception {
		String sql = "select " + addressFields + " from " + addressTableName + " where org_id=" + id;
		return jdbcTemplate.query(sql, new OrganizationAddressRowMapper());
	}

	@Override
	public List<String> getOrganizationCategoriesById(int id) throws Exception {
		String sql = "SELECT name FROM " + orgCatTableName + " where id in (SELECT org_category_id from "
				+ orgCatRefTableName + " where org_id=" + id + ")";
		return jdbcTemplate.query(sql, new OrganizationCategoryRowMapper());
	}

	@Override
	public int getOrganizationRatingsById(int id) throws Exception {
		String sql = "SELECT AVG(ratings) as ratings FROM review where org_id=" + id;
		return jdbcTemplate.query(sql, new OrganizationRatingsRowMapper()).get(0);
	}

	public List<Integer> getOrganizationDetailsByAreaOrCategory(OrganizationSearchModel model) {
		String sql = "";
		String sqlCatId = "";
		String sqlState = "";
		List<Integer> catIds = model.getCategoriesId();
		List<String> states = model.getState();
		if (catIds.size() > 0) {
			String id = "";
			for (int i = 0; i < catIds.size(); i++) {
				if (i == catIds.size() - 1) {
					id = id + catIds.get(i);
					break;
				}
				id = id + catIds.get(i) + ",";
			}
			sqlCatId = "(org_category_reference.org_category_id in (" + id
					+ ") and org_category_reference.org_id = organization.id)";
		}
		if (states.size() > 0) {
			String state = "";
			for (int i = 0; i < states.size(); i++) {
				if (i == states.size() - 1) {
					state = state + "'" + states.get(i) + "'";
					break;
				}
				state = state + "'" + states.get(i) + "',";
			}
			sqlState = "(org_address.state in (" + state
					+ ") and org_address.org_id = organization.id and org_address.is_active = true)";
		}
		if (sqlCatId.equals("") && sqlState.equals("")) {
			sql = "select id from organization where is_active=true limit 5";
		} else if (!sqlCatId.equals("") && !sqlState.equals("")) {
			sql = "SELECT DISTINCT organization.id from org_address,org_category_reference,organization where ("
					+ sqlCatId + " or " + sqlState + ") and organization.is_active = true";
		} else {
			sql = "SELECT DISTINCT organization.id from org_address,org_category_reference,organization where ("
					+ sqlCatId + sqlState + ") and organization.is_active = true";
		}
		return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("id"));
	}

	@Override
	public int addOrganization(OrganizationWithPassword organizationWithPassword) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		organizationWithPassword.setActive(false);
		organizationWithPassword.setVerified(false);
		jdbcTemplate.update(queryGenerator.generatePreparedStatementInsertQuery(tableName, organizationWithPassword),
				new BeanPropertySqlParameterSource(organizationWithPassword), holder);
		return holder.getKey().intValue();
	}

	@Override
	public boolean updateOrganization(int id, Organization organization) throws Exception {
		String sql = "UPDATE `" + tableName + "` set "
				+ "`full_name` = :fullname, `phone`= :phone, `description`=:description, `email`=:email where `id`=:id";
		organization.setId(id);
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(organization)) > 0;
	}

	@Override
	public boolean updateActiveStatus(int id, boolean isActive) throws Exception {
		String sql = "UPDATE `" + tableName + "` set `is_active`=:active where `id`=:id";
		Organization organization = new Organization();
		organization.setId(id);
		organization.setActive(isActive);
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(organization)) > 0;
	}

	@Override
	public boolean updateVerifiedStatus(int id, boolean isVerified) throws Exception {
		String sql = "UPDATE `" + tableName + "` set `is_verified`=:verified where `id`=:id";
		Organization organization = new Organization();
		organization.setId(id);
		organization.setVerified(isVerified);
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(organization)) > 0;
	}

	@Override
	public int updatePassword(OrganizationWithNewPassword organizationWithNewPassword) throws Exception {
		String selectSQL = "select id from " + tableName + " where id=:id and password=:oldPassword";
		if (jdbcTemplate.queryForList(selectSQL, new BeanPropertySqlParameterSource(organizationWithNewPassword))
				.size() > 0) {
			String sql = "UPDATE `" + tableName + "` set `password`=:newPassword  where `id`=:id";
			return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(organizationWithNewPassword));
		}
		return -1;
	}

	@Override
	public int addAddress(OrganizationAddress organizationAddress) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		organizationAddress.setActive(true);
		jdbcTemplate.update(
				queryGeneratorAddress.generatePreparedStatementInsertQuery(addressTableName, organizationAddress),
				new BeanPropertySqlParameterSource(organizationAddress), holder);
		return holder.getKey().intValue();
	}

	@Override
	public int updateAddress(OrganizationAddress organizationAddress) throws Exception {
		String sql = "UPDATE `" + addressTableName + "` set "
				+ "`area` = :area, `state`= :state, `city`=:city, `is_active`=:active where `id`=:id";
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(organizationAddress));
	}

	@Override
	public boolean updateActiveStatusOfAddress(int id, boolean isActive) throws Exception {
		String sql = "UPDATE `" + addressTableName + "` set `is_active`=:active where `id`=:id";
		Organization organization = new Organization();
		organization.setId(id);
		organization.setActive(isActive);
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(organization)) > 0;

	}

	@Override
	public List<OrganizationCategories> getAllCategories() throws Exception {
		String sql = "select * from " + orgCatTableName;
		return jdbcTemplate.query(sql, new CategoryRowMapper());
	}

	@Override
	public List<OrganizationCategories> getAllCategoriesOfOrg(int id) {
		String sql = "select org_category.id,org_category.name from org_category,org_category_reference"
				+ " where org_category_reference.org_category_id = org_category.id and "
				+ "org_category_reference.org_id=" + id;
		return jdbcTemplate.query(sql, new CategoryRowMapper());
	}

	@Override
	public int addCategory(OrganizationCategoryRefrence obj) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(queryGeneratorCategory.generatePreparedStatementInsertQuery(orgCatRefTableName, obj),
				new BeanPropertySqlParameterSource(obj), holder);
		return holder.getKey().intValue();
	}

	@Override
	public List<Integer> checkVolunteer(int orgId, int userId) {
		String sql = "select id from volunteer where org_id=" + orgId + " and user_id=" + userId;
		return jdbcTemplate.query(sql, (rs, next) -> rs.getInt("id"));
	}

}
