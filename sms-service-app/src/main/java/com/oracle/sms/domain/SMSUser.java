/**
 * 
 */
package com.oracle.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.oracle.sms.constants.UserRole;
import com.oracle.sms.dto.SMSUserDetail;
import com.oracle.sms.utils.PasswordEncryptor;

/**
 * @author nithin james
 *
 *         Entity holds user information
 */
@Entity
public class SMSUser extends SMSDomain {

	@Column(nullable = false)
	private String userName;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Column
	private String password;

	public SMSUser(String userName, UserRole userRole, String password) {
		super();
		this.userName = userName;
		this.userRole = userRole;
		this.password = PasswordEncryptor.encrypt(password);
	}

	public SMSUser() {
		super();
	}

	public SMSUserDetail mapResponse() {

		return new SMSUserDetail(id, userName, userRole.getRole());
	}

	public boolean verifyPassword(String password) {

		return this.password.equals(PasswordEncryptor.encrypt(password));
	}

}
