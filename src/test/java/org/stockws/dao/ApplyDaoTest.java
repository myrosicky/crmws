package org.stockws.dao;

import static org.junit.Assert.fail;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.stockws.config.TestConfig;

@RunWith(SpringRunner.class)
public class ApplyDaoTest extends TestConfig {

	private static final Logger log = LoggerFactory.getLogger(ApplyDaoTest.class);
	
	@Autowired
	private ApplyDao applyDao;
	
	@Test
	public final void testSave() {
		Apply apply = new Apply()
			.setCountry("cn")
			.setArea("asia")
			.setProvince("gd")
			.setCity("gz")
			.setIp("127.0.0.1")
			.setNumber("1")
			.setType("1")
			;
		applyDao.save(apply);
	}

	@Test
	public final void testFindByCountry() {
		
	}

	@Test
	public final void testFindByCountryAndArea() {
		List<Apply> applys = applyDao.findAll();
		log.debug("applys:" + applys);
		if(applys != null){
			log.debug("applys.size():" + applys.size());
		}
	}

	@Test
	public final void testFindByCountryAndAreaAndCity() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCountApplysById() {
		
	}

	
	
}
