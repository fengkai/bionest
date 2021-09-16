package com.zkteco.bionest.autoconfigure.data.jpa;

import com.zkteco.bionest.autoconfigure.data.jpa.entity.City;
import com.zkteco.bionest.autoconfigure.data.jpa.repository.CityRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;


/**
 * Test for {@link JpaAutoconfiguration}
 *
 * @author Tyler Feng
 */
@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-jpa.properties")
class JpaAutoconfigurationTests {

	@Autowired
	private CityRepository cityRepository;

	@Test
	void dataJpaAutoconfigurationWorks(){
		City city = new City();
		city.setName("test1");
		cityRepository.save(city);
	}

	@SpringBootApplication
	@ComponentScan("com.zkteco.bionest.autoconfigure.data.jpa.repository")
	static class JpaConfiguration{

	}

}