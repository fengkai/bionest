package com.zkteco.bionest.autoconfigure.security.oauth2;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * RSA generator.
 *
 * @author Tyler Feng
 */
final class RSAGenerateUtils {

	private RSAGenerateUtils() {

	}

	/**
	 * Generate key pair.
	 * @return
	 */
	static KeyPair keyPair() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
		return keyPair;
	}

}
