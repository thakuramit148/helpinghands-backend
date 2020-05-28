package com.helpinghands.organization.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrganizationDetail extends Organization{
	private int ratings;
	private List<String> categories;
	private List<OrganizationAddress> address;

	public void setDetails(Organization org) {
		super.setId(org.getId()); 
		super.setUsername((org.getUsername())); 
		super.setFullname(org.getFullname()); 
		super.setEmail(org.getEmail()); 
		super.setPhone(org.getPhone()); 
		super.setDescription(org.getDescription()); 
		super.setActive(org.getActive()); 
		super.setVerified(org.getVerified()); 
	}
	
}
