package com.helpinghands.organization.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpinghands.exception.ResultException;
import com.helpinghands.organization.model.Event;
import com.helpinghands.organization.repository.EventRepository;
import com.helpinghands.response.Result;

@Service
public class EventService {

	List<String> statusOfDonation = new ArrayList<>(Arrays.asList(new String[] { "Approving", "Declined", "Assigning",
			"In progress", "Dropped", "Completed", "Donated", "Canceled" }));

	@Autowired
	EventRepository eventRepository;

	public Result<List<Event>> findAllEventsByOrgId(int orgId) {
		List<Event> list = eventRepository.findAllEventsByOrgId(orgId);
		if (list.size() > 0) {
			return new Result<>(200, list);
		}
		throw new ResultException(new Result<>(404, "you have not added any events to your organization yet!"));
	}

	public Result<Event> addEvent(Event model) throws Exception {
		int eventId = eventRepository.addEvent(model);
		if (eventId > 0) {
			model.setId(eventId);
			return new Result<>(200, model);
		}
		throw new ResultException(
				new Result<>(404, "Error! something went wrong while adding the event! please try again"));
	}

	public Result<String> updateEvent(Event model) {
		if (eventRepository.updateEvent(model) > 0) {
			return new Result<>(201, "successfully updated the status");
		}
		throw new ResultException(new Result<>(404, "Error in updating the event! id is-invalid. please try again"));
	}
}