package com.helpinghands.organization.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.helpinghands.organization.model.Event;

@Repository
public interface EventRepository {
	public List<Event> findAllEventsByOrgId(int orgId);

	public int addEvent(Event model) throws Exception;

	public int updateEvent(Event model);
}
