package com.helpinghands.organization.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.helpinghands.organization.model.Event;
import com.helpinghands.queryhelper.QueryGenerator;

@Repository
public class EventRepositoryImpl implements EventRepository {

	static String fields = "`id`, `name`, `start`, `end`, `description`, `org_id`";
	static String tableName = "event";
	private QueryGenerator<Event> queryGenerator = new QueryGenerator<Event>();

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Event> findAllEventsByOrgId(int orgId) {
		String sql = "select " + fields + " from " + tableName + " where org_id = " + orgId;
		return jdbcTemplate.query(sql, (rs, n) -> new Event(rs.getInt("id"), rs.getString("name"), rs.getDate("start"),
				rs.getDate("end"), rs.getString("description"), rs.getInt("org_id")));
	}

	@Override
	public int addEvent(Event model) throws Exception {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(queryGenerator.generatePreparedStatementInsertQuery(tableName, model),
				new BeanPropertySqlParameterSource(model), holder);
		return holder.getKey().intValue();
	}

	@Override
	public int updateEvent(Event model) {
		String sql = "update " + tableName
				+ " set name=:name, start=:start, end=:end, description=:description where id=:id";
		SqlParameterSource map = new MapSqlParameterSource().addValue("id", model.getId())
				.addValue("start", model.getStart()).addValue("end", model.getEnd())
				.addValue("description", model.getDescription()).addValue("name", model.getName());
		return jdbcTemplate.update(sql, map);
	}
}
