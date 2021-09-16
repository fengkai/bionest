package com.zkteco.bionest.autoconfigure.security.servlet;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.zkteco.bionest.autoconfigure.context.MessageSourceAutoconfiguration;
import com.zkteco.bionest.autoconfigure.security.jwt.Jwks;
import com.zkteco.bionest.autoconfigure.web.WebAutoconfiguration;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SecurityAutoConfigurationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void login() throws Exception{
		this.mockMvc.perform(post("/login?username=user1&password=password")).andExpect(status().isOk())
				.andExpect(content().string(containsString("tokens")));
	}

	@SpringBootApplication
	@ComponentScan("com.zkteco.bionest.autoconfigure.security.servlet")
	@Import({MessageSourceAutoconfiguration.class, WebAutoconfiguration.class})
	static class TestConfig {

		@Bean
		public JWKSource<SecurityContext> jwkSource() {
			RSAKey rsaKey = Jwks.generateRsa();
			JWKSet jwkSet = new JWKSet(rsaKey);
			return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
		}

		@Bean
		UserDetailsService users() {
			UserDetails user = User.withDefaultPasswordEncoder()
					.username("user1")
					.password("password")
					.roles("USER")
					.build();
			return new InMemoryUserDetailsManager(user);
		}
	}

}