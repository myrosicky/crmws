package org.stockws.controller;

import java.util.ArrayList;
import java.util.List;

import org.business.models.Menu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menus")
public class MenuController {

	@GetMapping("")
	public List<Menu> getALLMenu(Menu menu){
		List<Menu> menus = new ArrayList<>(1);
		Menu tmp = new Menu();
		tmp.setName("apply");
		tmp.setId(1l);
		tmp.setRole("0");
		menus.add(tmp);
		return menus;
	}
}
