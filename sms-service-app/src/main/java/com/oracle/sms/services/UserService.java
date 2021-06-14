package com.oracle.sms.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.oracle.sms.dto.RegisterRequest;
import com.oracle.sms.dto.SMSUserDetail;
import com.oracle.sms.exceptions.InvalidPassword;
import com.oracle.sms.exceptions.UserExistsException;
import com.oracle.sms.exceptions.UserNotFound;

@Component
public interface UserService {

	SMSUserDetail create(RegisterRequest registerRequest) throws UserExistsException;

	List<SMSUserDetail> getAll();

	SMSUserDetail get(String userName) throws UserNotFound;

	SMSUserDetail login(String userName, String password) throws UserNotFound, InvalidPassword;

}
