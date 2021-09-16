package com.zkteco.bionest.autoconfigure.security.oauth2;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * JwksEndpointFilter test.
 *
 * @author Tyler Feng
 */
@WebMvcTest
@ImportAutoConfiguration(OAuth2ResourceServerAutoConfiguration.class)
class JwksEndpointFilterTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void getJwksJsonWorks() throws Exception {
		this.mvc.perform(get("/.well-known/jwks.json")).andExpect(status().isOk())
				.andExpect(content().string(containsString("keys")));
	}

	@Test
	void returnTokenWhenLogin() throws Exception {
		this.mvc.perform(post("/login?username=user1&password=password")).andExpect(status().isOk())
				.andExpect(content().string(containsString("token2")));
	}

	@SpringBootApplication
	@Import({ JwkSourceAutoConfiguration.class, Oauth2SecurityAutoConfiguration.class })
	static class UserConfiguration {

		@Bean
		UserDetailsService users() {
			UserDetails user = User.withDefaultPasswordEncoder()
					.username("user1")
					.password("password")
					.roles("USER")
					.build();
			return new InMemoryUserDetailsManager(user);
		}

		@Bean
		public JwtDecoder jwtDecoder() {
			return new JwtDecoder() {
				@Override
				public Jwt decode(String token) throws JwtException {
					return null;
				}
			};
		}
	}

}