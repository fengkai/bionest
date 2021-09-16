package com.zkteco.user.application;

import java.util.Set;

import com.zkteco.bionest.boot.web.PageResponse;
import com.zkteco.user.application.command.UserAddCommand;
import com.zkteco.user.application.command.UserAddRoleCommand;
import com.zkteco.user.application.dto.UserDto;
import com.zkteco.user.application.query.UserPageQuery;
import com.zkteco.user.domain.entity.Role;

import org.springframework.lang.Nullable;

/**
 * User application service.
 *
 * @author Tyler
 */
public interface UserService {

	/**
	 * Delegate domain to save user.
	 * @param cmd
	 * @return
	 */
	Boolean save(UserAddCommand cmd);

	UserDto queryById(Long id);

	PageResponse<UserDto> queryByPage(UserPageQuery query);

	UserDto addRole(UserAddRoleCommand command);
}
