package com.zkteco.bionest.user.types.authority;

import lombok.Getter;

@Getter
public class AuthorityType {

	private final String type;

	public AuthorityType(String type) {
		this.type = type;
	}

}
