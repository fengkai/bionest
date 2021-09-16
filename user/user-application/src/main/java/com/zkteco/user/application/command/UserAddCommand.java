package com.zkteco.user.application.command;

import lombok.Data;

/**
 * User add command.
 *
 * @author TylerFeng
 */
@Data
public class UserAddCommand {

	private String userName;

	private String password;

}
