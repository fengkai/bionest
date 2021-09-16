package com.zkteco.bionest.user.types.authority;

import lombok.Getter;

@Getter
public class AuthorityCode {

	private final String code;

	public AuthorityCode(String code) {
		this.code = code;
	}

}
