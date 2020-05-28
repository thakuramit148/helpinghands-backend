package com.helpinghands.user.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.helpinghands.queryhelper.QueryGenerator;
import com.helpinghands.user.model.OrganizationVolunteer;
import com.helpinghands.user.model.User;
import com.helpinghands.user.model.UserWithNewPassword;
import com.helpinghands.user.model.UserWithPassword;
import com.helpinghands.user.repository.mapper.UserRowMapper;

@Repository(value = "userRepo")
public class UserRepositoryImpl implements UserRepository {

	static String fields = "`id`, `username`, `full_name`, `email`, `phone`, `address`, `is_active`";
	static String tableName = "user";
	private QueryGenerator<UserWithPassword> queryGenerator = new QueryGenerator<UserWithPassword>();

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<User> findAllUsers() {
		String sql = "select " + fields + " from " + tableName;
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	@Override
	public List<User> findUserById(int id) throws Exception {
		String sql = "select " + fields + " from " + tableName + " where id=" + id;
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	@Override
	public int addUser(UserWithPassword userWithPassword) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		userWithPassword.setActive(true);
		jdbcTemplate.update(queryGenerator.generatePreparedStatementInsertQuery(tableName, userWithPassword),
				new BeanPropertySqlParameterSource(userWithPassword), holder);
		return holder.getKey().intValue();
	}

	@Override
	public boolean updateUser(int id, User user) throws Exception {
		String sql = "UPDATE `" + tableName + "` set "
				+ "`full_name` = :fullname, `phone`= :phone, `address`=:address, `email`=:email where `id`=:id";
		user.setId(id);
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user)) > 0;
	}

	@Override
	public boolean updateActiveStatus(int id, boolean isActive) throws Exception {
		String sql = "UPDATE `" + tableName + "` set `is_active`=:active where `id`=:id";
		User user = new User();
		user.setId(id);
		user.setActive(isActive);
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user)) > 0;
	}

	@Override
	public int updatePassword(UserWithNewPassword userWithNewPassword) throws Exception {
		String selectSQL = "select id from " + tableName + " where id=:id and password=:oldPassword";
		if (jdbcTemplate.queryForList(selectSQL, new BeanPropertySqlParameterSource(userWithNewPassword)).size() > 0) {
			String sql = "UPDATE `" + tableName + "` set `password`=:newPassword  where `id`=:id";
			return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(userWithNewPassword));
		}
		return -1;
	}

	@Override
	public List<OrganizationVolunteer> getOrganizationVolunteer(int userId) {
		String sql = "SELECT volunteer.org_id as orgId, volunteer.id as volId,organization.full_name as name FROM `volunteer`,`organization`"
				+ " WHERE organization.id=volunteer.org_id AND volunteer.user_id=" + userId;
		return jdbcTemplate.query(sql,
				(rs, next) -> new OrganizationVolunteer(rs.getInt("orgId"), rs.getString("name"), rs.getInt("volId")));
	}
}
