package com.zkteco.bionest.user.persistence.dao;

import com.zkteco.bionest.user.persistence.AuthorityDO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Authority data access object.
 *
 * @author Tyler Feng
 */
public interface AuthorityDAO extends JpaRepository<AuthorityDO, Long>, JpaSpecificationExecutor<Long> {
}
