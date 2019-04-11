package org.stockws.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

public class CipherUtil {

	private final static Logger logger = LoggerFactory
			.getLogger(CipherUtil.class);

	private final static String SALT = "LLisMoon";

	// public static String RSAAndMD5(String originText,String salt) throws
	// UnsupportedEncodingException, NoSuchAlgorithmException {
	//
	// MessageDigestPasswordEncoder encoder = new Md5PasswordEncoder();
	// String hashedPass = encoder.encodePassword(originText, salt);
	// return hashedPass;
	// }

	public static String BCryptEncode(String originText) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		String hash = encoder.encode(originText + SALT);
		logger.info("BCryptEncode(originalText:" + originText + ",hash:" + hash
				+ ")");
		return hash;
	}

	public static boolean BCryptMatch(String originText, String hash) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		return encoder.matches(originText + SALT, hash);
	}

	public static String MD5(String plainText) {
		return MD5(plainText, SALT);
	}

	public static String MD5(String plainText, Object salt) {
		return DigestUtils.md5DigestAsHex(plainText.getBytes());
//		return new MessageDigestPasswordEncoder("MD5").encodePassword(plainText, salt);
	}

	public static boolean MD5Match(String plainText, String hash) {
		return MD5Match(plainText, SALT,hash);
	}

	public static boolean MD5Match(String plainText, String salt, String hash) {
		return hash.equals(DigestUtils.md5DigestAsHex(plainText.getBytes()));
	}

	public void name() throws CertificateException, KeyStoreException,
			NoSuchAlgorithmException, IOException, InvalidKeyException,
			NoSuchProviderException, SignatureException {
		KeyStore p12 = KeyStore.getInstance("jks");
		CertificateFactory certificatefactory = CertificateFactory
				.getInstance("X.509");
		FileInputStream fileinputstream = new FileInputStream(new File(
				"D:/gdldweb.jks"));
		PublicKey publicKey1 = null;
		p12.load(fileinputstream, "gdldweb".toCharArray());
		Enumeration e = p12.aliases();
		while (e.hasMoreElements()) {
			String alias = (String) e.nextElement();
			System.out.println(alias);

			X509Certificate c = (X509Certificate) p12.getCertificate(alias);
			c.checkValidity();
			if ("caroot".equals(alias)) {
				publicKey1 = c.getPublicKey();
			}
			Principal subject = c.getSubjectX500Principal();
			String subjectArray[] = subject.toString().split(",");
			for (String s : subjectArray) {
				String[] str = s.trim().split("=");
				String key = str[0];
				String value = str[1];
				System.out.println(key + " - " + value);
			}
			System.err.println(c.getPublicKey());
		}

		p12 = KeyStore.getInstance("pkcs12");
		fileinputstream = new FileInputStream(new File(
				"D:/gdldwebClient.pkcs12"));
		PublicKey publicKey2 = null;
		p12.load(fileinputstream, "1234567".toCharArray());
		e = p12.aliases();
		while (e.hasMoreElements()) {
			String alias = (String) e.nextElement();
			System.out.println(alias);

			X509Certificate c = (X509Certificate) p12.getCertificate(alias);
			c.checkValidity();
			c.verify(publicKey1);

			Principal subject = c.getSubjectX500Principal();
			String subjectArray[] = subject.toString().split(",");
			for (String s : subjectArray) {
				String[] str = s.trim().split("=");
				String key = str[0];
				String value = str[1];
				System.out.println(key + " - " + value);
			}
			System.err.println(c.getPublicKey());
		}

	}

	public static void main(String[] args) throws CertificateException,
			KeyStoreException, NoSuchAlgorithmException, IOException,
			InvalidKeyException, NoSuchProviderException, SignatureException {
		new CipherUtil().name();
	}
}
