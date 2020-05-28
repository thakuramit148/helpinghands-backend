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

import com.helpinghands.organization.model.Organization;
import com.helpinghands.organization.model.OrganizationAddress;
import com.helpinghands.organization.model.OrganizationCategories;
import com.helpinghands.organization.model.OrganizationCategoryRefrence;
import com.helpinghands.organization.model.OrganizationDetail;
import com.helpinghands.organization.model.OrganizationSearchModel;
import com.helpinghands.organization.model.OrganizationWithNewPassword;
import com.helpinghands.organization.model.OrganizationWithPassword;
import com.helpinghands.organization.service.OrganizationService;
import com.helpinghands.response.Result;

@RestController()
@RequestMapping(path = "/organization", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController {

	@Autowired
	OrganizationService organizationService;

	@GetMapping("/")
	public ResponseEntity<Result<List<Organization>>> getAllOrganizations() {
		Result<List<Organization>> result = organizationService.findAllOrganizations();
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Result<Organization>> getOrganizationById(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
		Result<Organization> result = organizationService.findOrganizationsById(id);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@GetMapping("/{id}/details")
	public ResponseEntity<Result<OrganizationDetail>> getOrganizationDetailsById(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
		Result<OrganizationDetail> result = organizationService.findOrganizationDetaislById(id);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/search")
	public ResponseEntity<Result<List<OrganizationDetail>>> getOrganizationDetailsByAreaOrCategory(
			@RequestBody(required = true) @Valid OrganizationSearchModel searchModel) throws Exception {
		Result<List<OrganizationDetail>> result = organizationService
				.getOrganizationDetailsByAreaOrCategory(searchModel);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/")
	public ResponseEntity<Result<Organization>> addOrganization(
			@RequestBody(required = true) @Valid OrganizationWithPassword organizationWithPassword) throws Exception {
		Result<Organization> result = organizationService.addOrganization(organizationWithPassword);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Result<Organization>> updateOrganization(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id,
			@RequestBody(required = true) @Valid Organization organization) throws Exception {
		Result<Organization> result = organizationService.updateOrganization(id, organization);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/{id}/active/{status}")
	public ResponseEntity<Result<String>> updateActiveStatus(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id,
			@PathVariable("status") @Valid @Pattern(regexp = "(true|false)") boolean status) throws Exception {
		Result<String> result = organizationService.updateActiveStatus(id, status);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/{id}/verify/{status}")
	public ResponseEntity<Result<String>> updateVeirifedStatus(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id,
			@PathVariable("status") @Valid @Pattern(regexp = "(true|false)") boolean status) throws Exception {
		Result<String> result = organizationService.updateVerifiedStatus(id, status);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/")
	public ResponseEntity<Result<String>> updatePassword(
			@RequestBody(required = true) @Valid OrganizationWithNewPassword organizationWithNewPassword)
			throws Exception {
		Result<String> result = organizationService.updatePassword(organizationWithNewPassword);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/address")
	public ResponseEntity<Result<OrganizationAddress>> addOrganizationAddress(
			@RequestBody(required = true) @Valid OrganizationAddress organizationAddress) throws Exception {
		Result<OrganizationAddress> result = organizationService.addAddress(organizationAddress);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("/address/{id}")
	public ResponseEntity<Result<OrganizationAddress>> updatePassword(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id,
			@RequestBody(required = true) @Valid OrganizationAddress organizationAddress) throws Exception {
		organizationAddress.setId(id);
		Result<OrganizationAddress> result = organizationService.updateAddress(organizationAddress);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PutMapping("address/{id}/active/{status}")
	public ResponseEntity<Result<String>> updateActiveStatusOfAddress(
			@PathVariable("id") @Valid @Pattern(regexp = "[0-9]*") int id,
			@PathVariable("status") @Valid @Pattern(regexp = "(true|false)") boolean status) throws Exception {
		Result<String> result = organizationService.updateActiveStatusOfAddress(id, status);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@GetMapping("/categories")
	public ResponseEntity<Result<List<OrganizationCategories>>> getAllCategories() throws Exception {
		Result<List<OrganizationCategories>> result = organizationService.getAllCategories();
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@GetMapping("/categories/{orgId}")
	public ResponseEntity<Result<List<OrganizationCategories>>> getAllCategoriesOfOrg(
			@PathVariable("orgId") @Valid @Pattern(regexp = "[0-9]*") int id) throws Exception {
		Result<List<OrganizationCategories>> result = organizationService.getAllCategoriesOfOrg(id);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}

	@PostMapping("/category")
	public ResponseEntity<Result<OrganizationCategoryRefrence>> addOrganizationCategory(
			@RequestBody(required = true) @Valid OrganizationCategoryRefrence organizationCategoryRefrence)
			throws Exception {
		Result<OrganizationCategoryRefrence> result = organizationService.addCategoryRef(organizationCategoryRefrence);
		return new ResponseEntity<>(result, HttpStatus.valueOf(result.getCode()));
	}
}
