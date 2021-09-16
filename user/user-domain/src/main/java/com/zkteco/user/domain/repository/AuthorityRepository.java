package com.zkteco.user.domain.repository;

import com.zkteco.user.domain.entity.Authority;

/**
 * Authority repository.
 */
public interface AuthorityRepository {

	/**
	 * Save
	 * @param authority
	 */
	void save(Authority authority);

}
