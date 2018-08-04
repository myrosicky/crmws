package service.iface;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.stockws.context.WebMvcContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(classes={WebMvcContext.class,org.stockws.context.DataSourceConfig.class,org.stockws.context.DispatcherServletInitializer.class,org.stockws.context.JpaContext.class,org.stockws.context.RedisContext.class})
public class TaskServiceTest {

	@Test
	public void testReceiveTasks() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveTask() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveTask() {
		fail("Not yet implemented");
	}

}
