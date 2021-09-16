package com.zkteco.user.application;

import com.zkteco.user.application.command.RoleAddCommand;

/**
 * Role application service.
 */
public interface RoleService {

	void save(RoleAddCommand command);

}
