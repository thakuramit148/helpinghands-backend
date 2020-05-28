package com.helpinghands.admin.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.helpinghands.admin.model.user.User;
import com.helpinghands.response.Result;

@FeignClient(url = "http://localhost:8081/user", name = "USER-CLIENT")
public interface UserClient {

	@GetMapping("/")
	public Result<List<User>> getAllUsers();
}
