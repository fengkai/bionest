package com.zkteco.bionest.autoconfigure.security;

import com.zkteco.bionest.autoconfigure.context.MessageSourceAutoconfiguration;
import com.zkteco.bionest.autoconfigure.web.WebAutoconfiguration;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureWebMvc
@WebMvcTest
public class DefaultBootConfigure {

	private MockMvc mockMvc;

	@Test
	void localInterceptorWorks() throws Exception {
		this.mockMvc.perform(get("/test/language?language=en_US")).andExpect(status().isOk())
				.andExpect(content().string(containsString("test")));
		this.mockMvc.perform(get("/test/language?language=zh_CN")).andExpect(status().isOk())
				.andExpect(content().string(containsString("测试")));
	}

	@SpringBootApplication
	@ComponentScan("com.zkteco.bionest.autoconfigure.security")
	@Import({ MessageSourceAutoconfiguration.class, WebAutoconfiguration.class })
	static class TestConfig {

	}

}
