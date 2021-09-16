package com.zkteco.bionest.user.types.user;

import lombok.Getter;

@Getter
public class Password {

	private String value;

	public Password(String value) {
		this.value = value;
	}

}
