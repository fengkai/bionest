package com.zkteco.bionest.autoconfigure.security.oauth2;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.zkteco.bionest.autoconfigure.security.GenerateTokenSuccessHandler;
import com.zkteco.bionest.autoconfigure.security.jwt.JwtEncoder;
import com.zkteco.bionest.autoconfigure.security.jwt.NimbusJwsEncoder;
import com.zkteco.bionest.autoconfigure.security.oauth2.authserver.JwtHttpSecurityConfigure;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

/**
 * Autoconfigure for web security.
 *
 * @author Tyler Feng
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Import(JwkSourceAutoConfiguration.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Oauth2SecurityAutoConfiguration {

	@Bean
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		JwtHttpSecurityConfigure<HttpSecurity> httpSecurityConfigure = new JwtHttpSecurityConfigure();

		JwtEncoder jwtEncoder = getJwtEncoder(http);
		AuthenticationSuccessHandler successHandler = new GenerateTokenSuccessHandler(jwtEncoder);

		http.authorizeRequests()
//				.mvcMatchers("/test/**").anonymous()
				.mvcMatchers("/helloworld").anonymous()
				.mvcMatchers("/user/**").anonymous()
				.mvcMatchers("/role/**").anonymous()
				.anyRequest().authenticated().and().formLogin()
				.successHandler(successHandler)
				.and().httpBasic().and().csrf().disable().apply(httpSecurityConfigure);

		File publicKeyFile = new File("C:\\Users\\Administrator\\Desktop\\bionest\\autoconfigure\\public.key");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

		http.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwt -> jwt
//						.jwkSetUri("https://idp.example.com/.well-known/jwks.json")
										.decoder(NimbusJwtDecoder.withPublicKey(publicKey).build())
										.jwtAuthenticationConverter(jwtAuthenticationConverter())
						)
		);

		return http.build();
	}

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("test");
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("test_");
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}


	private static <B extends HttpSecurityBuilder<B>> JwtEncoder getJwtEncoder(B builder) {
		JwtEncoder jwtEncoder = builder.getSharedObject(JwtEncoder.class);
		if (jwtEncoder == null) {
			jwtEncoder = getOptionalBean(builder, JwtEncoder.class);
			if (jwtEncoder == null) {
				JWKSource<SecurityContext> jwkSource = getJwkSource(builder);
				jwtEncoder = new NimbusJwsEncoder(jwkSource);
			}
			builder.setSharedObject(JwtEncoder.class, jwtEncoder);
		}
		return jwtEncoder;
	}

	@SuppressWarnings("unchecked")
	private static <B extends HttpSecurityBuilder<B>> JWKSource<SecurityContext> getJwkSource(B builder) {
		JWKSource<SecurityContext> jwkSource = builder.getSharedObject(JWKSource.class);
		if (jwkSource == null) {
			ResolvableType type = ResolvableType.forClassWithGenerics(JWKSource.class, SecurityContext.class);
			jwkSource = getBean(builder, type);
			builder.setSharedObject(JWKSource.class, jwkSource);
		}
		return jwkSource;
	}


	@SuppressWarnings("unchecked")
	private static <B extends HttpSecurityBuilder<B>, T> T getBean(B builder, ResolvableType type) {
		ApplicationContext context = builder.getSharedObject(ApplicationContext.class);
		String[] names = context.getBeanNamesForType(type);
		if (names.length == 1) {
			return (T) context.getBean(names[0]);
		}
		if (names.length > 1) {
			throw new NoUniqueBeanDefinitionException(type, names);
		}
		throw new NoSuchBeanDefinitionException(type);
	}

	private static <B extends HttpSecurityBuilder<B>, T> T getOptionalBean(B builder, Class<T> type) {
		Map<String, T> beansMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(
				builder.getSharedObject(ApplicationContext.class), type);
		if (beansMap.size() > 1) {
			throw new NoUniqueBeanDefinitionException(type, beansMap.size(),
					"Expected single matching bean of type '" + type.getName() + "' but found " +
							beansMap.size() + ": " + StringUtils.collectionToCommaDelimitedString(beansMap.keySet()));
		}
		return (!beansMap.isEmpty() ? beansMap.values().iterator().next() : null);
	}

	//@formatter:on

}
