package com.zkteco.bionest.autoconfigure.security.oauth2;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.UUID;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Autoconfigure a jwk source with a RSA pair.
 *
 * @author Tyler Feng
 */
@Configuration
@ConditionalOnMissingBean(JWKSource.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
public class JwkSourceAutoConfiguration {

	@Bean
	public JWKSet jwkSet() throws Exception {
		File publicKeyFile = new File("C:\\Users\\Administrator\\Desktop\\bionest\\autoconfigure\\public.key");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

		File privateKeyFile = new File("C:\\Users\\Administrator\\Desktop\\bionest\\autoconfigure\\private.key");
		byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());

		PKCS8EncodedKeySpec spec =
				new PKCS8EncodedKeySpec(privateKeyBytes);
		RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(spec);

		RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString())
				.build();
		return new JWKSet(rsaKey);
	}

	@Bean
	@ConditionalOnMissingBean(JWKSource.class)
	public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {

		return new JWKSource<SecurityContext>() {
			@Override
			public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
				return jwkSelector.select(jwkSet);
			}
		};
	}

	@Bean
	public JwkStore jwkStore(JWKSet jwkSet) {
		return new JwkStore(jwkSet.toString());
	}

}
