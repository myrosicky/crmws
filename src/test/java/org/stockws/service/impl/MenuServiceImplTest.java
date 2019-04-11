package org.stockws.service.impl;

import java.util.List;

import org.business.models.Menu;
import org.business.models.MenuRole;
import org.business.models.applysystem.Dictionary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.stockws.config.TestConfig;
import org.stockws.service.MenuService;
import org.stockws.util.TimeUtil;


@RunWith(SpringRunner.class)
public class MenuServiceImplTest  extends TestConfig{

	private static final Logger log = LoggerFactory.getLogger(MenuServiceImplTest.class);
	
	@Autowired
	private MenuService menuService;
	
	@Test
	public final void testGetMenu() {
		log.debug("begin [testGetMenu]");
		long userId = 1l;
		List<Menu> menus = menuService.getMenu(userId);
		
		log.debug("menus:" + menus);
		log.debug("end [testGetMenu]");
	}

	@Test
	public final void testSaveMenu(){
		long userId = 1l;
		
		Menu menu = new Menu();
		menu.setName("apply");
		menuService.saveMenu(menu, userId);
		
		Menu menu2 = new Menu();
		menu.setName("report");
		menuService.saveMenu(menu2, userId);
		
		
	}
	
	@Test
	public final void testSaveMenuRole(){
		long userId = 1l;
		
		MenuRole menuRole = new MenuRole();
		menuRole.setMenuId(1l);
		menuRole.setRoleId(3l);
		menuService.saveMenuRole(menuRole, userId);
		
		MenuRole menuRole2 = new MenuRole();
		menuRole2.setMenuId(2l);
		menuRole2.setUserId(1l);
		menuService.saveMenuRole(menuRole2, userId);
		
		
		
	}
}
