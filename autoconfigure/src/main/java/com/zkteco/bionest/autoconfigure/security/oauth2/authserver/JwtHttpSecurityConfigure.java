package com.zkteco.bionest.autoconfigure.security.oauth2.authserver;

import com.zkteco.bionest.autoconfigure.security.oauth2.JwkStore;

import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * Custom  security configure.
 *
 * @author Tyler Feng
 */
public class JwtHttpSecurityConfigure<B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<JwtHttpSecurityConfigure<B>, B> {

	// Generate object.
	@Override
	public void init(B builder) throws Exception {
		// Generate jwk source through jwk set from user's defined.

	}

	// Configure HttpSecurityBuilder
	@Override
	public void configure(B builder) {
		ApplicationContext context = builder.getSharedObject(ApplicationContext.class);
		JwkStore jwkStore = context.getBean(JwkStore.class);
		JwksEndpointFilter jwksEndpointFilter = new JwksEndpointFilter(jwkStore);
		builder.addFilterBefore(postProcess(jwksEndpointFilter), AbstractPreAuthenticatedProcessingFilter.class);
	}

}
