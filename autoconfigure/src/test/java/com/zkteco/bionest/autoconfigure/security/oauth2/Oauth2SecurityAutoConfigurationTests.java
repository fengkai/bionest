package com.zkteco.bionest.autoconfigure.security.oauth2;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Arrays;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.zkteco.bionest.autoconfigure.security.jwt.JoseHeader;
import com.zkteco.bionest.autoconfigure.security.jwt.JwtClaimsSet;
import com.zkteco.bionest.autoconfigure.security.jwt.KeyGeneratorUtils;
import com.zkteco.bionest.autoconfigure.security.jwt.NimbusJwsEncoder;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class Oauth2SecurityAutoConfigurationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	private MockWebServer mockWebServer;

	@Autowired
	private ApplicationContext applicationContext;

	@BeforeEach
	void setUp() throws Exception {
		this.mockWebServer = new MockWebServer();
		this.mockWebServer.start();
	}

	@Test
	void jwksEndpointWorks() throws Exception {
		this.mockMvc.perform(get("/.well-known/jwks.json")).andExpect(status().isOk())
				.andExpect(content().string(containsString("RSA2")));
	}

	@Test
	void issueToken() {

	}

	@Test
	void useNimbusDecoder() throws Exception {
//		Map<String, MockResponse> responses = new HashMap<>();
//		responses
//				.put("http://127.0.0.1:8080/.well-known/jwks.json", response(JwkSet.JWK_SET));
//		this.mockWebServer.enqueue(response(JwkSet.JWK_SET));
//		Dispatcher dispatcher = new Dispatcher() {
//			@Override
//			public MockResponse dispatch(RecordedRequest request) {
//				// @formatter:off
//				return Optional.of(request)
//						.map(RecordedRequest::getRequestUrl)
//						.map(HttpUrl::toString)
//						.map(responses::get)
//						.orElse(new MockResponse().setResponseCode(404));
//				// @formatter:on
//			}
//		};
//		this.mockWebServer.setDispatcher(dispatcher);

		JwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri("http://127.0.0.1:8080/.well-known/jwks.json")
				.build();

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

		Jwt jwt = encoder.encode(headersBuilder.build(), claimsBuilder.build());


		KeyPair keyPair = applicationContext.getBean(KeyPair.class);
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		decoder = NimbusJwtDecoder.withPublicKey(publicKey).build();

//		JwtDecoders.fromIssuerLocation("http://127.0.0.1:8080");

		System.out.println(decoder.decode(jwt.getTokenValue()).getClaims());
	}

	private MockResponse response(String body) {
		// @formatter:off
		return new MockResponse()
				.setBody(body)
				.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		// @formatter:on
	}

	@Test
	void generateTokenWhenLoginSuccess() throws Exception {
		this.mockMvc.perform(post("/login?username=user1&password=password")).andExpect(status().isOk())
				.andExpect(content().string(containsString("token")));
	}

	@Test
	void defaultJwkSourceInvalidWhenCustomJwkSource() {
		JWKSource jwkSource = applicationContext.getBean(JWKSource.class);
		jwkSource.toString();
	}


	@SpringBootApplication
	@ComponentScan("com.zkteco.bionest.autoconfigure.security.oauth2")
	static class TestConfig {

//		@Bean
//		public KeyPair keyPair() {
//			KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
//			return keyPair;
//		}
//
//		@Bean
//		public JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
//			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//			RSAKey rsaKey = new RSAKey.Builder(publicKey)
//					.privateKey(privateKey)
//					.keyID("one")
//					.build();
////			RSAKey rsaKey = Jwks.generateRsa();
//			JWKSet jwkSet = new JWKSet(rsaKey);
//			return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
//		}

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