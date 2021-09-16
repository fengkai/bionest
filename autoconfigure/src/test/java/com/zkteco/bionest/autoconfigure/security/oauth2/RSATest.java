package com.zkteco.bionest.autoconfigure.security.oauth2;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import com.zkteco.bionest.autoconfigure.security.jwt.KeyGeneratorUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RSATest {

	// @formatter:off
	public static final String DEFAULT_RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDcWWomvlNGyQhA"
			+ "iB0TcN3sP2VuhZ1xNRPxr58lHswC9Cbtdc2hiSbe/sxAvU1i0O8vaXwICdzRZ1JM"
			+ "g1TohG9zkqqjZDhyw1f1Ic6YR/OhE6NCpqERy97WMFeW6gJd1i5inHj/W19GAbqK"
			+ "LhSHGHqIjyo0wlBf58t+qFt9h/EFBVE/LAGQBsg/jHUQCxsLoVI2aSELGIw2oSDF"
			+ "oiljwLaQl0n9khX5ZbiegN3OkqodzCYHwWyu6aVVj8M1W9RIMiKmKr09s/gf31Nc"
			+ "3WjvjqhFo1rTuurWGgKAxJLL7zlJqAKjGWbIT4P6h/1Kwxjw6X23St3OmhsG6HIn"
			+ "+jl1++MrAgMBAAECggEBAMf820wop3pyUOwI3aLcaH7YFx5VZMzvqJdNlvpg1jbE"
			+ "E2Sn66b1zPLNfOIxLcBG8x8r9Ody1Bi2Vsqc0/5o3KKfdgHvnxAB3Z3dPh2WCDek"
			+ "lCOVClEVoLzziTuuTdGO5/CWJXdWHcVzIjPxmK34eJXioiLaTYqN3XKqKMdpD0ZG"
			+ "mtNTGvGf+9fQ4i94t0WqIxpMpGt7NM4RHy3+Onggev0zLiDANC23mWrTsUgect/7"
			+ "62TYg8g1bKwLAb9wCBT+BiOuCc2wrArRLOJgUkj/F4/gtrR9ima34SvWUyoUaKA0"
			+ "bi4YBX9l8oJwFGHbU9uFGEMnH0T/V0KtIB7qetReywkCgYEA9cFyfBIQrYISV/OA"
			+ "+Z0bo3vh2aL0QgKrSXZ924cLt7itQAHNZ2ya+e3JRlTczi5mnWfjPWZ6eJB/8MlH"
			+ "Gpn12o/POEkU+XjZZSPe1RWGt5g0S3lWqyx9toCS9ACXcN9tGbaqcFSVI73zVTRA"
			+ "8J9grR0fbGn7jaTlTX2tnlOTQ60CgYEA5YjYpEq4L8UUMFkuj+BsS3u0oEBnzuHd"
			+ "I9LEHmN+CMPosvabQu5wkJXLuqo2TxRnAznsA8R3pCLkdPGoWMCiWRAsCn979TdY"
			+ "QbqO2qvBAD2Q19GtY7lIu6C35/enQWzJUMQE3WW0OvjLzZ0l/9mA2FBRR+3F9A1d"
			+ "rBdnmv0c3TcCgYEAi2i+ggVZcqPbtgrLOk5WVGo9F1GqUBvlgNn30WWNTx4zIaEk"
			+ "HSxtyaOLTxtq2odV7Kr3LGiKxwPpn/T+Ief+oIp92YcTn+VfJVGw4Z3BezqbR8lA"
			+ "Uf/+HF5ZfpMrVXtZD4Igs3I33Duv4sCuqhEvLWTc44pHifVloozNxYfRfU0CgYBN"
			+ "HXa7a6cJ1Yp829l62QlJKtx6Ymj95oAnQu5Ez2ROiZMqXRO4nucOjGUP55Orac1a"
			+ "FiGm+mC/skFS0MWgW8evaHGDbWU180wheQ35hW6oKAb7myRHtr4q20ouEtQMdQIF"
			+ "snV39G1iyqeeAsf7dxWElydXpRi2b68i3BIgzhzebQKBgQCdUQuTsqV9y/JFpu6H"
			+ "c5TVvhG/ubfBspI5DhQqIGijnVBzFT//UfIYMSKJo75qqBEyP2EJSmCsunWsAFsM"
			+ "TszuiGTkrKcZy9G0wJqPztZZl2F2+bJgnA6nBEV7g5PA4Af+QSmaIhRwqGDAuROR"
			+ "47jndeyIaMTNETEmOnms+as17g==";
	// @formatter:on

	@Test
	void print() {
		System.out.println(DEFAULT_RSA_PRIVATE_KEY);
		RSAPrivateKey defaultPrivateKey = TestKeys.DEFAULT_PRIVATE_KEY;
		System.out.println(Base64.getEncoder().encodeToString((defaultPrivateKey.getEncoded())));

		KeyGeneratorUtils.generateRsaKey();
	}

	@Test
	void generateRSAFile() throws Exception {
		KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		try (FileOutputStream fos = new FileOutputStream("public.key")) {
			fos.write(publicKey.getEncoded());
		}

		try (FileOutputStream fos = new FileOutputStream("private.key")) {
			fos.write(privateKey.getEncoded());
		}
	}

	@Test
	void readPrivateKeyFromFile() throws Exception {
		File publicKeyFile = new File("C:\\Users\\Administrator\\Desktop\\bionest\\autoconfigure\\private.der");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new PKCS8EncodedKeySpec(publicKeyBytes);
		PrivateKey publicKey = keyFactory.generatePrivate(publicKeySpec);
		System.out.println(publicKey.getClass().getName());
	}

	@Test
	void readPublicKeyFromFile() throws Exception {
		File publicKeyFile = new File("C:\\Users\\Administrator\\Desktop\\bionest\\autoconfigure\\public.der");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		System.out.println(publicKey.getClass().getName());
	}

	@Test
	void signTest() throws Exception {

		String originMessage = "Hello!";

		// Get private key.
		File privateFile = new File("C:\\Users\\Administrator\\Desktop\\bionest\\autoconfigure\\private.der");
		byte[] privateBytes = Files.readAllBytes(privateFile.toPath());
		KeyFactory privateFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateBytes);
		PrivateKey privateKey = privateFactory.generatePrivate(privateSpec);

		// Get public key.
		File publicKeyFile = new File("C:\\Users\\Administrator\\Desktop\\bionest\\autoconfigure\\public.der");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		// Sign
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(privateKey);
		signature.update(originMessage.getBytes(StandardCharsets.UTF_8));

		byte[] signed = signature.sign();

		String signedMessage = Base64.getEncoder().encodeToString(signed);

		System.out.println(signedMessage);


		// Verify
		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initVerify(publicKey);
		sign.update(originMessage.getBytes(StandardCharsets.UTF_8));

		boolean verifySuccess = sign.verify(Base64.getDecoder().decode(signedMessage.getBytes(StandardCharsets.UTF_8)));

		System.out.println(verifySuccess);

		// Encrypt.

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		String encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(originMessage.getBytes(StandardCharsets.UTF_8)));

		Cipher cipher2 = Cipher.getInstance("RSA");
		cipher2.init(Cipher.DECRYPT_MODE, privateKey);

		String result = new String(cipher2.doFinal(Base64.getDecoder().decode(encrypted)), StandardCharsets.UTF_8);

		assertEquals(result, "Hello!");

	}

}
