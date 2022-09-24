package com.book.request;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @author cogjava3180
 * LoginRequest bean is used for declaring login details
 *
 */

public class LoginRequest {
	@NotBlank
	private String userName;

	@NotBlank
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
