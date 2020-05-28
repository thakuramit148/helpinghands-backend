package com.helpinghands.admin.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.helpinghands.queryhelper.QueryGenerator;
import com.helpinghands.admin.model.admin.Admin;
import com.helpinghands.admin.model.admin.AdminWithNewPassword;
import com.helpinghands.admin.model.admin.AdminWithPassword;
import com.helpinghands.admin.repository.mapper.AdminRowMapper;

@Repository(value = "adminRepo")
public class AdminRepositoryImpl implements AdminRepository {

	static String fields = "`id`, `username`";
	static String tableName = "admin";
	private QueryGenerator<AdminWithPassword> queryGenerator = new QueryGenerator<AdminWithPassword>();

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Admin> findAllAdmins() {
		String sql = "select " + fields + " from " + tableName;
		return jdbcTemplate.query(sql, new AdminRowMapper());
	}

	@Override
	public List<Admin> findAdminById(int id) throws Exception {
		String sql = "select " + fields + " from " + tableName + " where id=" + id;
		return jdbcTemplate.query(sql, new AdminRowMapper());
	}

	@Override
	public int addAdmin(AdminWithPassword adminWithPassword) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(queryGenerator.generatePreparedStatementInsertQuery(tableName, adminWithPassword),
				new BeanPropertySqlParameterSource(adminWithPassword), holder);
		return holder.getKey().intValue();
	}

	@Override
	public int updatePassword(AdminWithNewPassword adminWithNewPassword) throws Exception {
		String selectSQL = "select id from " + tableName + " where id=:id and password=:oldPassword";
		if (jdbcTemplate.queryForList(selectSQL, new BeanPropertySqlParameterSource(adminWithNewPassword)).size() > 0) {
			String sql = "UPDATE `" + tableName + "` set `password`=:newPassword  where `id`=:id";
			return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(adminWithNewPassword));
		}
		return -1;
	}
}
