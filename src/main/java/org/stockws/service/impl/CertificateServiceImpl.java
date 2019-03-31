package org.stockws.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;

import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.business.models.Cert;
import org.business.models.CertSignReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.stockws.service.CertificateService;

import sun.security.x509.X509CertImpl;

@Service
public class CertificateServiceImpl implements CertificateService {

	private static final Logger log = LoggerFactory.getLogger(CertificateServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see org.stockws.service.impl.CertificateService#createKeystore(org.business.models.Cert)
	 */
	@Override
	public int createKeystore(Cert cert){
		String keystore = cert.getKeystore();
		String storePassword = cert.getStorePassword();
		String keyAlias = cert.getKeyAlias();
		String keyPassword = cert.getKeyPassword();
		String name = cert.getFirstAndLastName();
		String organizationalUnit = cert.getOrganizationalUnit();
		String organization = cert.getOrganization();
		String city = cert.getCity();
		String province = cert.getProvince();
		String countryCode = cert.getTwoLetterCountryCode();
		String signatureAlgorithm = cert.getSignatureAlgorithm();
		String keyAlgorithm = cert.getKeyAlgorithm();
		
		Security.addProvider(new BouncyCastleProvider());
		
		KeyPairGenerator keyPairGen = null;
		
		try {
			keyPairGen = KeyPairGenerator.getInstance(keyAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			log.error(null, e);
			return -1;
		}
		
		keyPairGen.initialize(2048, new SecureRandom());
		KeyPair pair = keyPairGen.generateKeyPair();
		SubjectPublicKeyInfo pubInfo = SubjectPublicKeyInfo.getInstance(pair.getPublic().getEncoded());
		
		X500Name issuer = new X500Name("CN=" + name + ", OU=" + organizationalUnit + ", O=" + organization + ", L=" + city + ", ST=" + province + ", C=" + countryCode);
		X500Name subject = issuer;
		
		Calendar c = Calendar.getInstance();
		Date startDate = c.getTime(); 		// time from which certificate is valid
		c.add(Calendar.YEAR, 1);
		Date expiryDate = c.getTime();  	// time after which certificate is valid
		BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
		
		X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
				issuer,		// issuer (CA)
				serialNumber,
				startDate, expiryDate,
				subject,
				pubInfo
		);
		
		// extension
		try {
			Extension extension = new Extension(Extension.subjectKeyIdentifier, false, new JcaX509ExtensionUtils().createSubjectKeyIdentifier(pair.getPublic()).getEncoded());
			certBuilder.addExtension(extension);
		} catch (NoSuchAlgorithmException | IOException e) {
			log.error(null, e);
			return -1;
		}
		
		// signature for sig
		ContentSigner sigGen = null;
		
		try {
			sigGen = new JcaContentSignerBuilder(signatureAlgorithm).build(pair.getPrivate());
		} catch (OperatorCreationException e) {
			log.error(null, e);
			return -1;
		}
		X509CertificateHolder certHolder = certBuilder.build(sigGen);
		X509Certificate caCert = null;
		
		try {
			caCert = new JcaX509CertificateConverter().getCertificate(certHolder);
		} catch (CertificateException e) {
			log.error(null, e);
			return -1;
		}
		
		X509Certificate[] chain = new X509Certificate[]{caCert};
		
		PrivateKeyEntry privateKeyEntry = new PrivateKeyEntry(pair.getPrivate(), chain); 
		
		KeyStore store = null;
		
		try {
			store = KeyStore.getInstance("JKS");
			store.load(null, null);
			store.setEntry(keyAlias, privateKeyEntry, new KeyStore.PasswordProtection(keyPassword.toCharArray()));
			store.store(Files.newOutputStream(Paths.get(keystore)), storePassword.toCharArray());
			
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			log.error(null, e);
			return -1;
		}
		log.info("keystore generted[" + keystore +"]");
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see org.stockws.service.impl.CertificateService#sigData(java.lang.String, byte[])
	 */
	@Override
	public byte[] sigData(String dataStr, byte[] privateKeyEncoded){
		
		KeyFactory keyFactory = null;
		
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			log.error(null, e);
		}
		
		X509EncodedKeySpec privateKeySpec = new X509EncodedKeySpec(privateKeyEncoded); 
		PrivateKey privateKey = null;
		
		try {
			privateKey = keyFactory.generatePrivate(privateKeySpec);
		} catch (InvalidKeySpecException e) {
			log.error(null, e);
		}
		
		byte[] data = dataStr.getBytes();
		
		try {
			Signature sig = Signature.getInstance("RSA");
			sig.initSign(privateKey);
			sig.update(data);
			data = sig.sign();
		} catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException e) {
		}
		return data;
	}
	
	/* (non-Javadoc)
	 * @see org.stockws.service.impl.CertificateService#verfifyData(java.lang.String, byte[], java.lang.String)
	 */
	@Override
	public int verfifyData(String dataStr, byte[] publicKeyEncoded, String authorSignature){
		
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			log.error(null, e);
			return -1;
		}
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyEncoded);
		PublicKey pubKey = null;
		try {
			pubKey = keyFactory.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException e) {
			log.error(null, e);
			return -1;
		}
		
		try {
			Signature sig = Signature.getInstance("RSA");
			sig.initVerify(pubKey);
			sig.update(dataStr.getBytes());
			if(sig.verify(authorSignature.getBytes())){
				return 0;
			}else{
				return -2;
			}
		} catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException e) {
			log.error(null, e);
			return -1;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.stockws.service.impl.CertificateService#importTrustKeystore(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int importTrustKeystore(String keystore, String keyAlias, String importCer, String storePassword){
		
		try {
			Path importJKS = Paths.get(keystore);
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(Files.newInputStream(importJKS), storePassword.toCharArray());
			
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(Files.newInputStream(Paths.get(importCer)));
			ks.setCertificateEntry(keyAlias, cert);
			ks.store(Files.newOutputStream(importJKS), storePassword.toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException e) {
			log.error(null, e);
			return -1;
		}
		
		return 0;
	}
	
	private PKCS10CertificationRequest parseCSR(String csrFilePath){
		PemObject tmp = null;
		PemReader reader = null;

		try {
			reader = new PemReader(Files.newBufferedReader(Paths.get(csrFilePath)));
			tmp = reader.readPemObject();
		} catch (IOException e) {
			log.error(null, e);
			return null;
		}
		
		return new PKCS10CertificationRequest (CertificationRequest.getInstance(tmp.getContent()));
	}
	
	/* (non-Javadoc)
	 * @see org.stockws.service.impl.CertificateService#signCertificate(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int signCertificate(String inputCSR, String keystore, String storePassword, String keyAlias, String keyPassword, String outputCER){
		KeyStore store = loadKeyStore(keystore, storePassword);
		PrivateKeyEntry privateKeyEntry = null;
		
		try {
			privateKeyEntry = (PrivateKeyEntry) store.getEntry(keyAlias, new KeyStore.PasswordProtection(keyPassword.toCharArray()));
		} catch (NoSuchAlgorithmException | UnrecoverableEntryException
				| KeyStoreException e) {
			log.error(null, e);
			return -1;
		}
		PrivateKey privateKey = privateKeyEntry.getPrivateKey();
		Certificate certificate = null;
		try {
			certificate = store.getCertificate(keyAlias);
		} catch (KeyStoreException e) {
			log.error(null, e);
			return -1;
		}
		
		PKCS10CertificationRequest csr = parseCSR(inputCSR); 
		Calendar c = Calendar.getInstance();
		Date startDate = c.getTime();
		c.add(Calendar.YEAR, 1);
		Date expiryDate = c.getTime();
		BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
		
		X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
				new X500Name(((X509CertImpl)certificate).getIssuerX500Principal().getName()), // issuer(CA)
				serialNumber,
				startDate, expiryDate,
				csr.getSubject(),
				csr.getSubjectPublicKeyInfo()
		);
		
		// signature for sig
		ContentSigner sigGen = null;
		try {
			sigGen = new JcaContentSignerBuilder("SHA256withRSA").build(privateKey);
		} catch (OperatorCreationException e) {
			log.error(null, e);
			return -1;
		}
		
		X509CertificateHolder certHolder = certBuilder.build(sigGen);
		X509Certificate caCert = null;
		try {
			caCert = new JcaX509CertificateConverter().getCertificate(certHolder);
		} catch (CertificateException e) {
			log.error(null, e);
			return -1;
		}
		
		writeCertToFile(caCert, outputCER);
		return 0;
		
	}
	
	private KeyStore loadKeyStore(String keystore, String storePassword){
		KeyStore store = null;
		try {
			store = KeyStore.getInstance("JKS");
			store.load(Files.newInputStream(Paths.get(keystore)), storePassword.toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException e) {
			log.error(null, e);
		}
		return store;
	}
	
	
	/* (non-Javadoc)
	 * @see org.stockws.service.impl.CertificateService#exportCert(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int exportCert(String keystore, String storePassword, String exportKeyAlias, String exportCer){
		KeyStore store = loadKeyStore(keystore, storePassword);
		Certificate certificate = null;
		try {
			certificate = store.getCertificate(exportKeyAlias);
		} catch (KeyStoreException e) {
			log.error(null, e);
			return -1;
		}
		
		writeCertToFile(certificate, exportCer);
		log.info("certificate exported[" + exportCer + "]");
		return 0;
	}
	
	private int writeCertToFile(Certificate certificate, String exportCerFile){
		OutputStream os = null;
		try {
			os = Files.newOutputStream(Paths.get(exportCerFile));
			os.write(certificate.getEncoded());
			os.flush();
		} catch (CertificateEncodingException | IOException e) {
			log.error(null, e);
			return -1;
		} finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					log.error(null, e);
					return -1;
				}
			}
		}
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see org.stockws.service.impl.CertificateService#viewCSR(org.business.models.CertSignReq)
	 */
	@Override
	public int viewCSR(CertSignReq certSignReq){
		PKCS10CertificationRequest csr = parseCSR(certSignReq.getFile());
		log.info("cert sign request:");
		log.info("Subject:" + csr.getSubject());
		
		return 1;
	}
	
	/* (non-Javadoc)
	 * @see org.stockws.service.impl.CertificateService#genCSR(org.business.models.CertSignReq)
	 */
	@Override
	public int genCSR(CertSignReq certSignReq){
		String keystore = certSignReq.getKeystore();
		String storePassword = certSignReq.getStorePassword();
		String exportKeyAlias = certSignReq.getKeyAlias();
		String exportKeyPassword= certSignReq.getKeyPassword();
		String exportCSR = certSignReq.getFile();
		String signatureAlgorithm = certSignReq.getSignatureAlgorithm();
		
		KeyStore store = loadKeyStore(keystore, storePassword);
		
		PrivateKey privateKey = null;
		Certificate certificate = null;
		PrivateKeyEntry entry = null;
		
		try {
			entry = (PrivateKeyEntry)store.getEntry(exportKeyAlias, new KeyStore.PasswordProtection(exportKeyPassword.toCharArray()));
			privateKey = entry.getPrivateKey();
			certificate = store.getCertificate(exportKeyAlias);
		} catch (NoSuchAlgorithmException | UnrecoverableEntryException
				| KeyStoreException e) {
			log.error(null, e);
			return -1;
		}
		
		PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
				((X509CertImpl)certificate).getSubjectX500Principal(),
				certificate.getPublicKey()
				);
		
		JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder(signatureAlgorithm);
		ContentSigner signer = null;
		try {
			signer = csBuilder.build(privateKey);
		} catch (OperatorCreationException e) {
			log.error(null, e);
			return -1;
		}
		PKCS10CertificationRequest csr = p10Builder.build(signer);
		
		JcaPEMWriter writer = null;
		try {
			writer = new JcaPEMWriter(new FileWriter(new File(exportCSR)));
			writer.writeObject(csr);
			writer.flush();
		} catch (IOException e) {
			log.error(null, e);
			return -1;
		} finally{
			try {
				if(writer != null){
					writer.close();
				}
			} catch (IOException e) {
				log.error(null, e);
				return -1;
			}
		}
		
		log.info("CSR generated[" + exportCSR + "]");
		return 0;
	}
	
	
}
