package com.zkteco.bionest.user.persistence.dao;

import com.zkteco.bionest.user.persistence.UserDO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User data access object.
 *
 * @author Tyler Feng
 */
public interface UserDAO extends JpaRepository<UserDO, Long>, JpaSpecificationExecutor<Long> {
}
