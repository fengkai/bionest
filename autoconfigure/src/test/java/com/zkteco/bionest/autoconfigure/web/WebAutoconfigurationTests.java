//package com.zkteco.bionest.autoconfigure.web;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.support.RequestContextUtils;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = WebAutoconfigurationTests.MyConfiguration.class)
//@WebAppConfiguration
//@AutoConfigureWebMvc
//@AutoConfigureMockMvc
////@WebMvcTest
////@ContextConfiguration(classes = WebAutoconfigurationTests.MyConfiguration.class)
//class WebAutoconfigurationTests {
//
//	@Autowired
//	private WebApplicationContext webApplicationContext;
//
//	private MockMvc mockMvc;
//
//	@BeforeEach
//	void setUp() {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
//	}
//
//	@Test
//	void localInterceptorWorks() throws Exception {
//		this.mockMvc.perform(get("/language?language=en_US")).andExpect(status().isOk())
//				.andExpect(content().string(containsString("test")));
//		this.mockMvc.perform(get("/language?language=zh_CN")).andExpect(status().isOk())
//				.andExpect(content().string(containsString("测试")));
//	}
//
//	@RestController
//	static class TestController {
//		@Autowired
//		MessageSource messageSource;
//
//		@GetMapping("/language")
//		public String test(HttpServletRequest httpServletRequest) {
//			return messageSource.getMessage("sss", null, RequestContextUtils.getLocale(httpServletRequest));
//		}
//	}
//
////	@SpringBootApplication
////	static class TestConfig {
////
////	}
//
////	@SpringBootApplication
//	@ComponentScan("com.zkteco.bionest.autoconfigure.web")
////	@Import({TestController.class, WebAutoconfiguration.class})
//	static class MyConfiguration {
//
//	}
//
//}