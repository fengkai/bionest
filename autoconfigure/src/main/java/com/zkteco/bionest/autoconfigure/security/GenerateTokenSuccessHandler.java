package com.zkteco.bionest.autoconfigure.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkteco.bionest.autoconfigure.security.jwt.JoseHeader;
import com.zkteco.bionest.autoconfigure.security.jwt.JwtClaimsSet;
import com.zkteco.bionest.autoconfigure.security.jwt.JwtEncoder;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Token generator. Should be invoked after success authenticated.
 *
 * @author Tyler Feng
 */
public class GenerateTokenSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtEncoder jwtEncoder;

	public GenerateTokenSuccessHandler(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
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
		claimsBuilder.claim("test", "Administrator");

		Jwt jwt = this.jwtEncoder.encode(headersBuilder.build(), claimsBuilder.build());

		// Write.
		response.getWriter().write("token " + jwt.getTokenValue());
	}
}
