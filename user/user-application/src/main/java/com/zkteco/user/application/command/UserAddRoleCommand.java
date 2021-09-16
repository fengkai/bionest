package com.zkteco.user.application.command;

import java.util.Set;

import lombok.Data;

@Data
public class UserAddRoleCommand {

	private Long userId;

	private Set<Long> roleIds;

}
