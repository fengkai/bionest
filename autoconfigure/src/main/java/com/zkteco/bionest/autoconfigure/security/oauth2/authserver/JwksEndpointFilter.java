package com.zkteco.bionest.autoconfigure.security.oauth2.authserver;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkteco.bionest.autoconfigure.security.oauth2.JwkStore;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * A {@code Filter} that processes jwks Requests.
 *
 * @author Tyler Feng
 */
public class JwksEndpointFilter extends OncePerRequestFilter {

	public static final String DEFAULT_JWKS_ENDPOINT_URL = "/.well-known/jwks.json";

	private final RequestMatcher requestMatcher = new AntPathRequestMatcher(
			DEFAULT_JWKS_ENDPOINT_URL,
			HttpMethod.GET.name()
	);

	private final JwkStore jwkStore;

	private final AbstractJsonHttpMessageConverter jsonHttpMessageConverter = new GsonHttpMessageConverter();

	public JwksEndpointFilter(JwkStore jwkStore) {
		this.jwkStore = jwkStore;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (!this.requestMatcher.matches(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

		jsonHttpMessageConverter.write(jwkStore.get(), MediaType.APPLICATION_JSON, httpResponse);

	}
}
