package com.helpinghands.authentication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.helpinghands.authentication.model.ProfileModel;
import com.helpinghands.authentication.repository.ProfileRepository;

@Service
public class ProfileService implements UserDetailsService {

	public static String tableName;

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final List<ProfileModel> users = profileRepository.getAllProfiles(tableName);
		for (ProfileModel appUser : users) {
			if (appUser.getUsername().equals(username)) {
				List<GrantedAuthority> grantedAuthorities = AuthorityUtils
						.commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole().toUpperCase());
				return new User(appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
			}
		}
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}

	public ProfileModel getProfileByUsername(String username) {
		return profileRepository.getProfileByUsername(username);
	}

}
