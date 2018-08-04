package org.stockws.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stockws.model.MenuDO;

@RestController
@RequestMapping("/menus")
public class MenuController {

	@GetMapping("")
	public List<MenuDO> getALLMenu(MenuDO menu){
		List<MenuDO> menus = new ArrayList<>(1);
		MenuDO tmp = new MenuDO();
		tmp.setName("apply");
		tmp.setId("0");
		tmp.setRole("0");
		menus.add(tmp);
		return menus;
	}
}
