package com.zkteco.bionest.user.types.user;

import lombok.Getter;

/**
 * User id
 */
@Getter
public class UserId {

	private final Long value;

	public UserId(Long value) {
		this.value = value;
	}

}
