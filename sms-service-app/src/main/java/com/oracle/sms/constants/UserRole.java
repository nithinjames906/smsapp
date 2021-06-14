package com.oracle.sms.constants;

public enum UserRole {
	ADMIN("Admin"), CUSTOMER("Customer"), ANALYTIC("Analytic");

	private String role;

	private UserRole(String role) {
		this.role = role;
	}

	public final String getRole() {
		return role;
	}

	public static UserRole get(String name) {
		for (UserRole roleValue : values()) {
			if (roleValue.role.equals(name)) {
				return roleValue;
			}
		}
		throw new IllegalArgumentException(name);
	}

}
