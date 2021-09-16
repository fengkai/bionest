package com.zkteco.bionest.autoconfigure.security.oauth2.resource;

import java.time.Instant;
import java.util.Arrays;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.zkteco.bionest.autoconfigure.security.jwt.JoseHeader;
import com.zkteco.bionest.autoconfigure.security.jwt.JwtClaimsSet;
import com.zkteco.bionest.autoconfigure.security.jwt.NimbusJwsEncoder;
import com.zkteco.bionest.autoconfigure.security.oauth2.JwkSourceAutoConfiguration;
import com.zkteco.bionest.autoconfigure.security.oauth2.Oauth2SecurityAutoConfiguration;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ResourceConfigurationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void resourceProtected() throws Exception {
		mvc.perform(get("/protected").header("Authorization", "Bearer xxxxxxxxxxxxxx")).andExpect(status().is4xxClientError()).andExpect(header().exists("WWW-Authenticate")).andExpect(header().string("WWW-Authenticate", containsString("Missing dot delimiter(s)")));
	}

	@Test
	void returnProtectedResourceWhenGivenRightToken() throws Exception {
		Jwt jwt = generateJwt();

		mvc.perform(get("/protected").header("Authorization", "Bearer " + jwt.getTokenValue())).andExpect(status().isOk());

	}

	@Test
	void loginReturnTokenCanAccessProtectedResource() throws Exception {
		String contentAsString = mvc.perform(post("/login").param("username", "user1").param("password", "password"))
				.andExpect(status().isOk()).andExpect(content().string(containsString("token"))).andReturn().getResponse().getContentAsString();
		String token = contentAsString.split(" ")[1];
		mvc.perform(get("/protected").header("Authorization", "Bearer " + token)).andExpect(status().isOk());
	}


	private Jwt generateJwt() {
		// Generate token.
		JWKSource jwkSource = applicationContext.getBean(JWKSource.class);

		NimbusJwsEncoder encoder = new NimbusJwsEncoder(jwkSource);

		// Generate token when success authenticated.
		JoseHeader.Builder headersBuilder = JoseHeader.withAlgorithm(SignatureAlgorithm.RS256);
		Instant issuedAt = Instant.now();
		// TODO Set expire time.
//		Instant expiresAt = issuedAt.plus(registeredClient.getTokenSettings().accessTokenTimeToLive());
		JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder();
		// TODO issuer setting
//		claimsBuilder.issuer(null);
		claimsBuilder
				.subject("subject")
				.audience(Arrays.asList("audienceList"))
				.issuedAt(issuedAt)
//				.expiresAt(expiresAt)
				.notBefore(issuedAt);

		// Custom field.
		claimsBuilder.claim("roles", "Administrator");
		claimsBuilder.claim("scope", "Administrator");
		claimsBuilder.claim("test", "Administrator");

		return encoder.encode(headersBuilder.build(), claimsBuilder.build());
	}

	@SpringBootApplication
	@Import({ JwkSourceAutoConfiguration.class, Oauth2SecurityAutoConfiguration.class })
	static class TestApplication {

		@RestController
		static class ProtectedController {

			@GetMapping("/protected")
			@PreAuthorize("hasAnyAuthority('test_Administrator')")
			public String protectedResource() {
				return "This is protected resource.";
			}

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
