package com.zkteco.bionest.user.repository.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.zkteco.bionest.boot.exception.BusinessException;
import com.zkteco.bionest.user.builder.RoleBuilder;
import com.zkteco.bionest.user.persistence.RoleDO;
import com.zkteco.bionest.user.persistence.dao.RoleDAO;
import com.zkteco.bionest.user.types.user.Password;
import com.zkteco.bionest.user.types.user.UserId;
import com.zkteco.bionest.user.types.user.UserName;
import com.zkteco.user.domain.entity.Role;
import com.zkteco.user.domain.entity.User;
import com.zkteco.user.domain.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class RoleRepositoryImpl implements RoleRepository {

	@Autowired
	private RoleDAO roleDao;

	@Autowired
	private RoleBuilder roleBuilder;

	@Override
	public Role find(Long id) {
		Optional<RoleDO> roleDOOptional = roleDao.findById(id);
		RoleDO roleDO = roleDOOptional.orElseThrow(() -> new BusinessException(String.format("角色[%s]不存在", id + "")));
		return roleBuilder.toRole(roleDO);
	}

	@Override
	public void save(Role role) {
		RoleDO roleDo = roleBuilder.fromRole(role);
		roleDao.save(roleDo);
	}

	@Override
	public Set<Role> findByIds(Set<Long> ids) {
		return roleDao.findByIdIn(ids);
	}
}
