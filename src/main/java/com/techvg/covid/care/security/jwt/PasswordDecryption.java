package com.techvg.covid.care.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.techvg.covid.care.web.rest.vm.LoginVM;

@Component
public class PasswordDecryption {

	/**
	 * Decryption of password that deals with OpenSSL encoded data applies with
	 * secret key in client side
	 * 
	 * @return decrypted password using AES algorithm & MD5 hashing technique
	 * @throws Exception
	 */

	public String decrypt(LoginVM loginVM) throws Exception {
		String decryptedText = null;
		String cipherText = loginVM.getPassword();

		try {
			String secret = "CcmsmhPasswordSecreateKey";

			byte[] cipherData = Base64.decodeBase64(cipherText);
			byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8),
					md5);
			SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
			IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

			byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
			Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
			byte[] decryptedData = aesCBC.doFinal(encrypted);
			decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

		} catch (Exception e) {
			throw e;
		}
		return decryptedText;
	}

	/**
	 * Generates a key and an initialization vector (IV) with the given salt and
	 * password.
	 * 
	 * @return an two-element array with the generated key and IV
	 */

	public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password,
			MessageDigest md) {
		int digestLength = md.getDigestLength();
		int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
		byte[] generatedData = new byte[requiredLength];
		int generatedLength = 0;
		try {
			md.reset();
			// Repeat process until sufficient data has been generated
			while (generatedLength < keyLength + ivLength) {
				// Digest data (last digest if available, password data, salt if available)
				if (generatedLength > 0)
					md.update(generatedData, generatedLength - digestLength, digestLength);
				md.update(password);
				if (salt != null)
					md.update(salt, 0, 8);
				md.digest(generatedData, generatedLength, digestLength);
				// additional rounds
				for (int i = 1; i < iterations; i++) {
					md.update(generatedData, generatedLength, digestLength);
					md.digest(generatedData, generatedLength, digestLength);
				}
				generatedLength += digestLength;
			}
			// Copy key and IV into separate byte arrays
			byte[][] result = new byte[2][];
			result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
			if (ivLength > 0)
				result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);
			return result;
		} catch (DigestException e) {
			throw new RuntimeException(e);
		} finally {
			// Clean out temporary data
			Arrays.fill(generatedData, (byte) 0);
		}
	}

}
