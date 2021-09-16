package com.zkteco.bionest.autoconfigure.context;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MessageSourceAutoconfiguration.class)
class MessageSourceAutoconfigurationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void messageSourceWorks(){
		assertThat(applicationContext.getMessage("sss", null, Locale.US)).isEqualTo("test");
		assertThat(applicationContext.getMessage("sss", null, Locale.SIMPLIFIED_CHINESE)).isEqualTo("测试");

	}


}