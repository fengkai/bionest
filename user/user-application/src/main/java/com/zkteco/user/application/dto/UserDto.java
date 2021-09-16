package com.zkteco.user.application.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UserDto {

	private Long id;

	private String userName;

	private String password;

	private Set<Long> roleIds;

}
