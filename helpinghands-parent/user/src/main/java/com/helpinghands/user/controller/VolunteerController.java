package com.helpinghands.user.controller;

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

import com.helpinghands.response.Result;
import com.helpinghands.user.model.UserDonationDetail;
import com.helpinghands.user.model.Volunteer;
import com.helpinghands.user.model.VolunteerPickup;
import com.helpinghands.user.service.VolunteerService;

@RestController()
@RequestMapping(path = "/volunteer", produces = MediaType.APPLICATION_JSON_VALUE)
public class VolunteerController {

	@Autowired
	VolunteerService volunteerService;

	@GetMapping("/pickup/{id}")
	public ResponseEntity<Result<List<UserDonationDetail>>> getAllPickupsByVolunteerId(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int volId) {
		Result<List<UserDonationDetail>> result = volunteerService.getAllPickupsByVolunteerId(volId);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/pickup")
	public ResponseEntity<Result<VolunteerPickup>> addVolunteerPickup(
			@RequestBody(required = true) @Valid VolunteerPickup volunteerPickup) throws Exception {
		Result<VolunteerPickup> result = volunteerService.addVolunteerPickup(volunteerPickup);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/")
	public ResponseEntity<Result<Volunteer>> addVolunteer(
			@RequestBody(required = true) @Valid Volunteer volunteer) throws Exception {
		Result<Volunteer> result = volunteerService.addVolunteer(volunteer);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/pickup/{id}/{status}/{donationId}")
	public ResponseEntity<Result<String>> updateVolunteerPickupStatus(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id, @PathVariable("status") String status,
			@PathVariable("donationId") @Valid @Pattern(regexp = "[0-9]*") int donationId) {
		Result<String> result = volunteerService.updateVolunteerPickupStatus(id, status, donationId);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

}
