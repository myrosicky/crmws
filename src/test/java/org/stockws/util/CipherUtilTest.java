package org.stockws.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;
import org.stockws.config.TestConfig;
import org.stockws.service.impl.ApplyServiceImplTest;

@RunWith(SpringRunner.class)
public class CipherUtilTest extends TestConfig {

	private static final Logger log = LoggerFactory.getLogger(CipherUtilTest.class);
	
	@Value("redis.password")
	private String redisPassword;
	
	@Test
	public final void testBCryptEncode() {
		log.info(CipherUtil.BCryptEncode(redisPassword));
	}

	@Test
	public final void testBCryptMatch() {
	}

	@Test
	public final void testMD5String() {
		log.info("password:" + redisPassword);
		log.info("md5(password):" + CipherUtil.MD5(redisPassword));
	}

	@Test
	public final void testMD5StringObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testMD5MatchStringString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testMD5MatchStringStringString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testName() {
		fail("Not yet implemented"); // TODO
	}

}
