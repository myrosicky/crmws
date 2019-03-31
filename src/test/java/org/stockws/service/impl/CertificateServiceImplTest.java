package org.stockws.service.impl;

import static org.junit.Assert.fail;

import org.business.models.Cert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.stockws.config.TestConfig;
import org.stockws.service.CertificateService;


@RunWith(SpringRunner.class)
public class CertificateServiceImplTest  extends TestConfig {

	@Autowired
	private CertificateService certificateService;
	
	@Autowired
	Cert initCert;
	
	@Test
	public final void testCreateKeystore() {
		certificateService.createKeystore(initCert);
	}

	@Test
	public final void testSigData() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testVerfifyData() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testImportTrustKeystore() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSignCertificate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testExportCert() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testViewCSR() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGenCSR() {
		fail("Not yet implemented"); // TODO
	}

}
