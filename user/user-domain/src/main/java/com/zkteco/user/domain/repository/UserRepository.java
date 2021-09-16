package com.zkteco.user.domain.repository;

import com.zkteco.bionest.boot.domain.PageQuery;
import com.zkteco.bionest.boot.web.PageResponse;
import com.zkteco.user.PageQuery;
import com.zkteco.user.domain.entity.User;

/**
 * User repository interface.
 *
 * @author Tyler Feng
 */
public interface UserRepository {

	User find(Long id);

	User save(User user);

	PageResponse<User> pageQuery(PageQuery<> pageQuery);

}
