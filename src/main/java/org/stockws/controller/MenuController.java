package org.stockws.controller;

import java.util.ArrayList;
import java.util.List;

import org.business.models.Menu;
import org.business.models.applysystem.dto.MenuDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menus")
public class MenuController {

	@GetMapping
	public MenuDTO getALLMenu(@RequestBody Menu menu){
		MenuDTO rtn = new MenuDTO();
		
		return rtn;
	}
}
