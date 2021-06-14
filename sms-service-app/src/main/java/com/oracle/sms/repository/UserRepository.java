package com.oracle.sms.repository;

import org.springframework.data.repository.CrudRepository;

import com.google.common.base.Optional;
import com.oracle.sms.domain.SMSUser;

public interface UserRepository extends CrudRepository<SMSUser, Long> {

	Optional<SMSUser> findByUserName(String userName);
}
