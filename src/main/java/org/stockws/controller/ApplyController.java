package org.stockws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stockws.model.Apply;
import org.stockws.service.ApplyService;

@RestController
@RequestMapping("/applys")
public class ApplyController {
	
	@Autowired
	private ApplyService applyService;
	
	@GetMapping("/{country}/{area}/{city}")
	public List<Apply> getALLApply(@PathVariable(required=false) String country, @PathVariable(required=false) String area, @PathVariable(required=false) String city){
		return applyService.query(country, area, city);
	}
	
	
}
