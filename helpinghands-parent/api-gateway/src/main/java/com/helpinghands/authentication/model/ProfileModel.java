package com.helpinghands.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileModel {
	private int id;
	private String username;
	private String password;
	private String role;
	private boolean isActive;	
}
