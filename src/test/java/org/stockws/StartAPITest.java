package org.stockws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.stockws.config.TestConfig;

@RunWith(SpringRunner.class)
public class StartAPITest extends TestConfig{

	@Test
	public final void testMain() {
		try {
			Thread.sleep(999999l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
