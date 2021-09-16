package com.zkteco.user.application.dto.assembler;

import java.util.HashSet;
import java.util.Set;

import com.zkteco.user.application.dto.UserDto;
import com.zkteco.user.domain.entity.Role;
import com.zkteco.user.domain.entity.User;
import lombok.Getter;

import org.springframework.util.CollectionUtils;

/**
 * Entity to dto.
 *
 * @author Tyler
 */
public class UserDtoAssembler {

	public static final UserDtoAssembler INSTANCE = new UserDtoAssembler();

	public UserDto user2Dto(User user) {
		UserDto userDto = new UserDto();
		userDto.setUserName(user.getUserName().getValue());
		userDto.setPassword(user.getPassword().getValue());
		userDto.setId(user.getId().getValue());
		
		if (!CollectionUtils.isEmpty(user.getRoleSet())) {
			Set<Long> roleIdSet = new HashSet<>();
			Set<Role> roleSet = user.getRoleSet();
			for (Role role : roleSet) {
				roleIdSet.add(role.getId());
				userDto.setRoleIds(roleIdSet);
			}
		}

		return userDto;
	}
}
