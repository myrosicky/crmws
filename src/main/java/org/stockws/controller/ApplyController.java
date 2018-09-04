package org.stockws.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.business.models.applysystem.Apply;
import org.business.models.applysystem.vo.QueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.stockws.service.ApplyService;

@RestController
@RequestMapping("/applys")
public class ApplyController {
	
	private static final Logger log = LoggerFactory.getLogger(ApplyController.class);
	
	@Autowired
	private ApplyService applyService;
	
	@GetMapping("/area/{area}/country/{country}/province/{province}/city/{city}?page={page}&size={size}")
	public List<Apply> getALLApply(
			@PathVariable(required=false) String area, 
			@PathVariable(required=false) String country, 
			@PathVariable(required=false) String province, 
			@PathVariable(required=false) String city,
			@RequestParam(required=false) String page,
			@RequestParam(required=false) String size){
		Apply apply = new Apply();
		apply.setArea(area);
		apply.setCountry(country);
		apply.setProvince(province);
		apply.setCity(city);
		if(log.isDebugEnabled()){
			log.debug("apply:" + apply);
		}
		QueryVO<Apply> queryVo = new QueryVO<>();
		queryVo.setModel(apply);
		if(StringUtils.isNotBlank(page)){
			queryVo.setPage(Integer.parseInt(page));
		}
		if(StringUtils.isNotBlank(size)){
			queryVo.setSize(Integer.parseInt(size));
		}
		
		return applyService.query(queryVo);
	}
	
	@PutMapping
	public int putApply(@RequestBody Apply apply){
		if(log.isDebugEnabled()){
			log.debug("apply:" + apply);
		}
		return applyService.add(apply);
	}
	
	@PostMapping
	public int postApply(@RequestBody Apply apply){
		if(log.isDebugEnabled()){
			log.debug("apply:" + apply);
		}
		return applyService.update(apply);
	}
	
	
}
