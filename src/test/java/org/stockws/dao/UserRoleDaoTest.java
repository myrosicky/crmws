package org.stockws.dao;

import org.business.models.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.stockws.config.TestConfig;

@RunWith(SpringRunner.class)
public class UserRoleDaoTest extends TestConfig {

	private static final Logger log = LoggerFactory.getLogger(UserRoleDaoTest.class);
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Test
	public final void testFindByUsername() {
		UserRole user = userRoleDao.findByUserId(1l);
		log.debug("user:" + user);
		log.debug("user.getUser():" + user.getUser());
	}

	
	
}
