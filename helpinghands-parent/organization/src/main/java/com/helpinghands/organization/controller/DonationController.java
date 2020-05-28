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

import com.helpinghands.organization.model.UserDonationDetail;
import com.helpinghands.organization.service.DonationService;
import com.helpinghands.response.Result;

@RestController()
@RequestMapping(path = "/donation", produces = MediaType.APPLICATION_JSON_VALUE)
public class DonationController {

	@Autowired
	DonationService donationService;

	@GetMapping("/user/{id}")
	public ResponseEntity<Result<List<UserDonationDetail>>> findAllDonationByUserId(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int userId) {
		Result<List<UserDonationDetail>> result = donationService.findAllDonationByUserId(userId);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@GetMapping("/organization/{id}")
	public ResponseEntity<Result<List<UserDonationDetail>>> findAllDonationByOrgId(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int orgId) {
		Result<List<UserDonationDetail>> result = donationService.findAllDonationByOrgId(orgId);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/user")
	public ResponseEntity<Result<UserDonationDetail>> addUserDonationToOrganization(
			@RequestBody(required = true) @Valid UserDonationDetail model) throws Exception {
		Result<UserDonationDetail> result = donationService.addUserDonationToOrganization(model);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/status/{id}/{status}")
	public ResponseEntity<Result<String>> updateDonationStatus(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int donationId,
			@PathVariable("status") String status) {
		Result<String> result = donationService.updateDonationStatus(donationId, status);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@GetMapping("/status/{id}")
	public ResponseEntity<Result<String>> getDonationStatusById(@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id) {
		Result<String> result = donationService.getDonationStatusById(id);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

}
