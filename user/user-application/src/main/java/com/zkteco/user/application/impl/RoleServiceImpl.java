package com.zkteco.user.application.impl;

import com.zkteco.user.application.RoleService;
import com.zkteco.user.application.command.RoleAddCommand;
import com.zkteco.user.domain.entity.Role;
import com.zkteco.user.domain.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Role service implementation
 *
 * @author Tyler Feng
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void save(RoleAddCommand command) {
		Role role = new Role();
		role.setName(command.getName());
		roleRepository.save(role);
	}
}
