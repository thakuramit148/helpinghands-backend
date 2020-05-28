package com.helpinghands.organization.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationSearchModel {
	List<Integer> categoriesId;
	List<String> state;
}