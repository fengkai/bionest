package com.zkteco.bionest.user.persistence.dao;

import com.zkteco.bionest.user.persistence.UserDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-jpa.properties")
class UserDAOTest {

	@Autowired
	private UserDAO userDAO;

	@BeforeEach
	void setUp() {
	}

	@SpringBootApplication
	@ComponentScan("com.zkteco.bionest.user.persistence")
	@EntityScan("com.zkteco.bionest.user.persistence")
	static class JpaConfiguration{

	}

	@Test
	void saveUserWorks() {
		UserDO userDO = new UserDO();
		userDO.setUserName("sss");
		userDO.setPassword("sss");
		UserDO save = userDAO.save(userDO);
	}

}