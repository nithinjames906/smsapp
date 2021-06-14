package com.oracle.sms.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.sms.constants.ResponseStatus;
import com.oracle.sms.dto.ApiResponse;
import com.oracle.sms.dto.LoginRequest;
import com.oracle.sms.dto.RegisterRequest;
import com.oracle.sms.exceptions.InvalidPassword;
import com.oracle.sms.exceptions.UserExistsException;
import com.oracle.sms.exceptions.UserNotFound;
import com.oracle.sms.services.UserService;

@RestController
public class AuthenticationResource {

	@Autowired
	UserService userService;

	private static final String AUTH_BASE_URL = "/api/v1/users";

	@PostMapping(path = AUTH_BASE_URL + "/register")
	public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest)
			throws UserExistsException {

		return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Surveys Fetched Successfully",
				userService.create(registerRequest)));
	}

	@PostMapping(path = AUTH_BASE_URL + "/login")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginReq) throws UserNotFound, InvalidPassword {

		return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS.getStatus(), "Surveys Fetched Successfully",
				userService.login(loginReq.getUserName(), loginReq.getPassword())));
	}

}
