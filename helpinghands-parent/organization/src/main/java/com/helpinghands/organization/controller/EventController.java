package com.helpinghands.organization.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpinghands.organization.model.Event;
import com.helpinghands.organization.service.EventService;
import com.helpinghands.response.Result;

@RestController()
@RequestMapping(path = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

	@Autowired
	EventService eventService;

	@GetMapping("/organization/{id}")
	public ResponseEntity<Result<List<Event>>> findAllDonationByUserId(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int orgId) {
		Result<List<Event>> result = eventService.findAllEventsByOrgId(orgId);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/")
	public ResponseEntity<Result<Event>> addUserDonationToOrganization(@RequestBody(required = true) @Valid Event model)
			throws Exception {
		Result<Event> result = eventService.addEvent(model);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/")
	public ResponseEntity<Result<String>> updateDonationStatus(@RequestBody(required = true) @Valid Event model) {
		Result<String> result = eventService.updateEvent(model);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}
}
