package com.zkteco.user.application.impl;

import java.util.Set;

import com.zkteco.bionest.boot.web.PageResponse;
import com.zkteco.bionest.user.types.user.Password;
import com.zkteco.bionest.user.types.user.UserName;
import com.zkteco.user.application.UserService;
import com.zkteco.user.application.command.UserAddCommand;
import com.zkteco.user.application.command.UserAddRoleCommand;
import com.zkteco.user.application.dto.UserDto;
import com.zkteco.user.application.dto.assembler.UserDtoAssembler;
import com.zkteco.user.application.query.UserPageQuery;
import com.zkteco.user.domain.entity.Role;
import com.zkteco.user.domain.entity.User;
import com.zkteco.user.domain.repository.RoleRepository;
import com.zkteco.user.domain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Boolean save(UserAddCommand cmd) {
		// New user.
		User user = new User();
		user.setUserName(new UserName(cmd.getUserName()));
		user.setPassword(new Password(cmd.getPassword()));

		// save.
		userRepository.save(user);

		return true;
	}

	@Override
	public UserDto queryById(Long id) {
		User user = userRepository.find(id);
		return UserDtoAssembler.INSTANCE.user2Dto(user);
	}

	@Override
	public PageResponse<UserDto> queryByPage(UserPageQuery query) {
		userRepository.pageQuery()
		return null;
	}

	@Override
	public UserDto addRole(UserAddRoleCommand command) {
		User user = userRepository.find(command.getUserId());
		Set<Role> roleSet = roleRepository.findByIds(command.getRoleIds());
		user = user.addRole(roleSet);
		userRepository.save(user);
		return UserDtoAssembler.INSTANCE.user2Dto(user);
	}
}
