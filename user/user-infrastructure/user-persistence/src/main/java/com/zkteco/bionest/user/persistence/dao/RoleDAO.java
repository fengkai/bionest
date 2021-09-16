package com.zkteco.bionest.user.persistence.dao;

import java.util.List;
import java.util.Set;

import com.zkteco.bionest.user.persistence.RoleDO;
import com.zkteco.user.domain.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Role data access object.
 *
 * @author Tyler Feng
 */
public interface RoleDAO extends JpaRepository<RoleDO, Long>, JpaSpecificationExecutor<Long> {

	Set<Role> findByIdIn(Set<Long> ids);

}
