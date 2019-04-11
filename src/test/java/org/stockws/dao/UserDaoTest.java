package org.stockws.dao;

import org.business.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.stockws.config.TestConfig;

@RunWith(SpringRunner.class)
public class UserDaoTest extends TestConfig {

	private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public final void testFindByUsername() {
		User user = userDao.findByUsername("u");
		log.debug("user:" + user);
	}

	
	
}
