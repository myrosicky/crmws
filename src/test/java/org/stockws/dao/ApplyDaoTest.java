package org.stockws.dao;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.stockws.model.Apply;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplyDaoTest {

	@Autowired
	private ApplyDao applyDao;
	
	@Test
	public final void testSave() {
		Apply apply = new Apply();
		apply.setCountry("cn");
		apply.setArea("asia");
		apply.setCity("gz");
		apply.setProvince("gd");
		apply.setIp("127.0.0.1");
		apply.setNumber("1");
		apply.setType("1");
		applyDao.save(apply);
	}

	@Test
	public final void testFindByCountry() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testFindByCountryAndArea() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testFindByCountryAndAreaAndCity() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCountApplysById() {
		fail("Not yet implemented"); // TODO
	}

	
	
}
