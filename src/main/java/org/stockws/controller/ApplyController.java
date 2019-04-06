package org.stockws.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.business.models.applysystem.Apply;
import org.business.models.applysystem.Approve;
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
	
	@GetMapping("")
	public List<Apply> getALLApplyByIds(@RequestParam("ids") List<Long> ids){
		List<Apply> applys = new ArrayList<>(ids.size());
		for(Long id : ids){
			Apply tmp = new Apply();
			tmp.setId(id);
			applys.add(tmp);
		}
		QueryVO<List<Apply>> queryVo = new QueryVO<>();
		queryVo.setModel(applys);
		return applyService.queryMulti(queryVo);
	}
			
	@GetMapping("/area/{area}/country/{country}/province/{province}/city/{city}")
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
	
	@PutMapping("/apply")
	public int putApply(@RequestBody Apply apply, HttpServletRequest req, Principal principal){
		if(log.isDebugEnabled()){
			log.debug("apply:" + apply);
		}
		
		String userID = null;
		return applyService.save(apply, req.getRemoteAddr(), userID);
	}
	
	@PostMapping("/apply")
	public int postApply(@RequestBody Apply apply, HttpServletRequest req, Principal principal){
		if(log.isDebugEnabled()){
			log.debug("apply:" + apply);
		}
		String userID = null;
		return applyService.save(apply, req.getRemoteAddr(), userID);
	}
	
	@PostMapping("/approve")
	public int postApprove(@RequestBody Approve approve){
		return applyService.approve(approve);
	}
	
	@PostMapping("/review")
	public int postReview(@RequestBody Approve approve){
		return applyService.review(approve);
	}
	
	@PostMapping("/return")
	public int postReturnBack(@RequestBody Approve approve){
		return applyService.returnBack(approve);
	}
	
	
	
	
}
