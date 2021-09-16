package com.zkteco.bionest.user.builder;

import java.util.HashSet;
import java.util.Set;

import com.zkteco.bionest.user.persistence.RoleDO;
import com.zkteco.bionest.user.persistence.UserDO;
import com.zkteco.bionest.user.types.user.Password;
import com.zkteco.bionest.user.types.user.UserId;
import com.zkteco.bionest.user.types.user.UserName;
import com.zkteco.user.domain.entity.Role;
import com.zkteco.user.domain.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class UserBuilder {

	@Autowired
	private RoleBuilder roleBuilder;

	/**
	 * Map do to user.
	 * @param userDO
	 * @return
	 */
	public User toUser(UserDO userDO) {
		User user = new User();
		user.setId(new UserId(userDO.getId()));
		user.setUserName(new UserName(userDO.getUserName()));
		user.setPassword(new Password(userDO.getPassword()));
		Set<RoleDO> roleDOSet = userDO.getRoleSet();
		if (!CollectionUtils.isEmpty(roleDOSet)) {
			Set<Role> roleSet = new HashSet<>();
			for (RoleDO roleDO : roleDOSet) {
				roleSet.add(roleBuilder.toRole(roleDO));
			}
			user.setRoleSet(roleSet);
		}
		return user;
	}


	/**
	 * Map user to do.
	 * @param user
	 * @return
	 */
	public UserDO fromUser(User user) {
		UserDO userDO = new UserDO();
		userDO.setId(user.getId() != null ? user.getId().getValue() : null);
		userDO.setUserName(user.getUserName().getValue());
		userDO.setPassword(user.getPassword().getValue());
		Set<Role> roleSet = user.getRoleSet();
		if (!CollectionUtils.isEmpty(roleSet)) {
			Set<RoleDO> roleDOSet = new HashSet<>();
			for (Role role : roleSet) {
				roleDOSet.add(roleBuilder.fromRole(role));
			}
			userDO.setRoleSet(roleDOSet);
		}
		return userDO;
	}

}
