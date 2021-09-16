package com.zkteco.user.domain.entity;

import java.util.HashSet;
import java.util.Set;

import com.zkteco.bionest.user.types.user.Password;
import com.zkteco.bionest.user.types.user.UserId;
import com.zkteco.bionest.user.types.user.UserName;
import lombok.Data;

import org.springframework.lang.Nullable;

/**
 * User entity.
 */
@Data
public class User {

	@Nullable
	private UserId id;

	private UserName userName;

	private Password password;

	private Set<Role> roleSet = new HashSet<>();

	/**
	 * Add role set.
	 * @param roleSet
	 */
	public User addRole(Set<Role> roleSet) {
		this.roleSet.addAll(roleSet);
		return this;
	}

}
