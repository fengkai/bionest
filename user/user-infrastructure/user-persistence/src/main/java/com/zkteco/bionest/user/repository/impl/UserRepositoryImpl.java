package com.zkteco.bionest.user.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.zkteco.bionest.boot.exception.BusinessException;
import com.zkteco.bionest.boot.web.PageResponse;
import com.zkteco.bionest.boot.web.PageReqBody;
import com.zkteco.bionest.user.builder.UserBuilder;
import com.zkteco.bionest.user.persistence.UserDO;
import com.zkteco.bionest.user.persistence.dao.UserDAO;
import com.zkteco.user.domain.entity.User;
import com.zkteco.user.domain.repository.UserRepository;
import org.hibernate.mapping.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private UserDAO userDao;

	@Autowired
	private UserBuilder userBuilder;

	@Override
	public User find(Long id) {
		Optional<UserDO> userDo = userDao.findById(id);
		UserDO userDO = userDo.orElseThrow(() -> new BusinessException(String.format("用户[%s]不存在", id + "")));
		return userBuilder.toUser(userDO);
	}

	@Override
	public User save(User user) {
		UserDO userDO = new UserDO();
		userDO.setUserName(user.getUserName().getValue());
		userDO.setPassword(user.getPassword().getValue());

		userDao.save(userDO);
		return null;
	}

	@Override
	public PageResponse<User> pageQuery(PageReqBody pageReqBody) {
		Page<UserDO> page = userDao.findAll(PageRequest.of(pageReqBody.getPageNumber(), pageReqBody.getPageSize()));
		PageResponse<User> pageResponse = new PageResponse<>();
		pageResponse.setTotal(page.getTotalElements());
		pageResponse.setPageNumber(page.getNumber());
		pageResponse.setPageSize(page.getSize());
		List<User> users = new ArrayList<>();
		for (UserDO userDO : page.getContent()) {
			users.add(userBuilder.toUser(userDO));
		}
		pageResponse.setData(users);
		return pageResponse;
	}
}
