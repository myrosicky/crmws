package org.stockws.service;

import org.business.models.Cert;
import org.business.models.CertSignReq;

public interface CertificateService {

	public int createKeystore(Cert cert);

	public byte[] sigData(String dataStr, byte[] privateKeyEncoded);

	public int verfifyData(String dataStr, byte[] publicKeyEncoded,
			String authorSignature);

	public int importTrustKeystore(String keystore, String keyAlias,
			String importCer, String storePassword);

	public int signCertificate(String inputCSR, String keystore,
			String storePassword, String keyAlias, String keyPassword,
			String outputCER);

	public int exportCert(String keystore, String storePassword,
			String exportKeyAlias, String exportCer);

	public int viewCSR(CertSignReq certSignReq);

	public int genCSR(CertSignReq certSignReq);

}