package com.zkteco.user.domain.repository;

import java.util.Set;

import com.zkteco.user.domain.entity.Role;
import com.zkteco.user.domain.entity.User;

import org.springframework.lang.Nullable;

/**
 * Role repository.
 */
public interface RoleRepository {

	Role find(Long id);

	/**
	 * Save
	 * @param role
	 */
	void save(Role role);

	Set<Role> findByIds(Set<Long> ids);

}
