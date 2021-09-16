package com.zkteco.bionest.user.web.dto.request;


import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * User add request body.
 *
 * @author Tyler Feng
 */
@Data
public class UserAddReqBody {

	@NotNull
	private String userName;

	@NotNull
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
