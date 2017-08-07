package org.stockws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {
	private final static Logger log = LoggerFactory.getLogger(StockController.class);
	
	@RequestMapping("/get20DayDiagram.do")
	public String get20DayDiagram(@RequestParam String stockID){
		
		return "hi," + stockID;
	}
}
