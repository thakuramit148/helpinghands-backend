package com.helpinghands.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileForLoginState {
	private int id;
	private String username;
	private String role;
	private boolean isActive;
}
