package com.helpinghands.employee.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.helpinghands.queryhelper.QueryGenerator;
import com.helpinghands.employee.model.Employee;
import com.helpinghands.employee.model.EmployeeWithNewPassword;
import com.helpinghands.employee.model.EmployeeWithPassword;
import com.helpinghands.employee.repository.mapper.EmployeeRowMapper;

@Repository(value = "employeeRepo")
public class EmployeeRepositoryImpl implements EmployeeRepository {

	static String fields = "`id`, `username`, `full_name`, `email`, `phone`, `address`, `is_active`, `org_id`";
	static String tableName = "employee";
	private QueryGenerator<EmployeeWithPassword> queryGenerator = new QueryGenerator<EmployeeWithPassword>();

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Employee> findAllEmployeesByOrgId(int orgId) {
		String sql = "select " + fields + " from " + tableName + " where org_id=" + orgId;
		return jdbcTemplate.query(sql, new EmployeeRowMapper());
	}

	@Override
	public List<Employee> findEmployeeById(int id) throws Exception {
		String sql = "select " + fields + " from " + tableName + " where id=" + id;
		return jdbcTemplate.query(sql, new EmployeeRowMapper());
	}

	@Override
	public int addEmployee(EmployeeWithPassword employeeWithPassword) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		employeeWithPassword.setActive(false);
		jdbcTemplate.update(queryGenerator.generatePreparedStatementInsertQuery(tableName, employeeWithPassword),
				new BeanPropertySqlParameterSource(employeeWithPassword), holder);
		return holder.getKey().intValue();
	}

	@Override
	public boolean updateEmployee(int id, Employee employee) throws Exception {
		String sql = "UPDATE `" + tableName + "` set "
				+ "`full_name` = :fullname, `phone`= :phone, `address`=:address, `email`=:email where `id`=:id";
		employee.setId(id);
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(employee)) > 0;
	}

	@Override
	public boolean updateActiveStatus(int id, boolean isActive) throws Exception {
		String sql = "UPDATE `" + tableName + "` set `is_active`=:active where `id`=:id";
		Employee employee = new Employee();
		employee.setId(id);
		employee.setActive(isActive);
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(employee)) > 0;
	}

	@Override
	public int updatePassword(EmployeeWithNewPassword employeeWithNewPassword) throws Exception {
		String selectSQL = "select id from " + tableName + " where id=:id and password=:oldPassword";
		if (jdbcTemplate.queryForList(selectSQL, new BeanPropertySqlParameterSource(employeeWithNewPassword))
				.size() > 0) {
			String sql = "UPDATE `" + tableName + "` set `password`=:newPassword  where `id`=:id";
			return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(employeeWithNewPassword));
		}
		return -1;
	}
	
	@Override
	public List<Integer> getOrganizationIdForEmployee(int id){
		String sql = "SELECT org_id FROM employee where id=" +id; 
		return jdbcTemplate.query(sql, (rs,next)-> rs.getInt("org_id"));
	}

}
