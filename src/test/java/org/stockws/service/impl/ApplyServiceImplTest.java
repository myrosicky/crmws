package org.stockws.service.impl;

import static org.junit.Assert.fail;

import org.business.models.applysystem.Apply;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.stockws.config.TestConfig;
import org.stockws.service.ApplyService;


@RunWith(SpringRunner.class)
public class ApplyServiceImplTest extends TestConfig {

	private static final Logger log = LoggerFactory.getLogger(ApplyServiceImplTest.class);
	
	@Autowired
	private ApplyService applyService;
	
	@Test
	public final void testQueryMulti() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testQuery() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSave() {
		log.debug("[testSave start]");
		Apply apply = new Apply();
		Apply[] p0s = {apply};
		for(Apply p0 : p0s){
			log.debug("----------------------");
			log.debug(p0 + "");
			int result = applyService.save(apply);
			log.debug("result:" + result);
		}
		log.debug("[testSave end]");
	}

	@Test
	public final void testDelete() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testApprove() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testReview() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testReturnBack() {
		fail("Not yet implemented"); // TODO
	}

}
