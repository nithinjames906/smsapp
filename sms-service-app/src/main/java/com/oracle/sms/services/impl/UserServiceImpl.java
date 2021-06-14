package com.oracle.sms.services.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.oracle.sms.constants.UserRole;
import com.oracle.sms.domain.SMSUser;
import com.oracle.sms.dto.RegisterRequest;
import com.oracle.sms.dto.SMSUserDetail;
import com.oracle.sms.exceptions.InvalidPassword;
import com.oracle.sms.exceptions.UserExistsException;
import com.oracle.sms.exceptions.UserNotFound;
import com.oracle.sms.repository.UserRepository;
import com.oracle.sms.services.UserService;

@Component
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Override
	public SMSUserDetail create(RegisterRequest registerRequest) throws UserExistsException {
		try {
			get(registerRequest.getUserName());
			throw new UserExistsException();
		} catch (UserNotFound e) {
			SMSUser smsUser = new SMSUser(registerRequest.getUserName(), UserRole.get(registerRequest.getUserRole()),
					registerRequest.getPassword());
			smsUser = userRepository.save(smsUser);
			logger.info("User created. id->" + smsUser.getId());
			return smsUser.mapResponse();
		}
	}

	@Override
	public List<SMSUserDetail> getAll() {
		List<SMSUserDetail> userResponses = Collections.emptyList();
		userRepository.findAll().forEach(user -> {
			userResponses.add(user.mapResponse());
		});
		return userResponses;
	}

	@Override
	public SMSUserDetail get(String userName) throws UserNotFound {
		Optional<SMSUser> user = userRepository.findByUserName(userName);
		if (!user.isPresent()) {
			logger.error("User not found. userName->" + userName);
			throw new UserNotFound();
		}
		return user.get().mapResponse();
	}

	@Override
	public SMSUserDetail login(String userName, String password) throws UserNotFound, InvalidPassword {
		Optional<SMSUser> user = userRepository.findByUserName(userName);
		if (!user.isPresent()) {
			logger.error("User not found. userName->" + userName);
			throw new UserNotFound();
		} else if (!user.get().verifyPassword(password)) {
			logger.error("Login with invalid password. userName->" + userName);
			throw new InvalidPassword();
		}
		return user.get().mapResponse();
	}

}
