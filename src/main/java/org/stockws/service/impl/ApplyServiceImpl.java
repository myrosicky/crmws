package org.stockws.service.impl;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stockws.dao.ApplyDao;
import org.stockws.service.ApplyService;
import org.stockws.util.TimeUtil;

import com.google.api.client.util.Strings;

@Service
public class ApplyServiceImpl implements ApplyService {

	private static final Logger log = LoggerFactory.getLogger(ApplyServiceImpl.class);
	
	@Autowired
	private ApplyDao applyDao;
	
	@Override
	public List<Apply> query(String area, String country, String province, String city){
		Apply input = new Apply();
		input.setArea(area);
		input.setCountry(country);
		input.setProvince(province);
		input.setCity(city);
		
		List<Apply> result = null;
		if(!Strings.isNullOrEmpty(input.getArea()) ){
			if(!Strings.isNullOrEmpty(input.getCountry())){
				if(!Strings.isNullOrEmpty(input.getProvince())){
					if(!Strings.isNullOrEmpty(input.getCity())){
						result = applyDao.findByAreaAndCountryAndProvinceAndCity(input.getArea(), input.getCountry(), input.getCity());
					}else{
						result = applyDao.findByAreaAndCountryAndProvince(input.getArea(), input.getCountry(), input.getProvince());
					}
				}else{
					result = applyDao.findByAreaAndCountry(input.getArea(), input.getCountry());
				}
			}else{
				result = applyDao.findByArea(input.getArea());
			}
		}
		if(log.isDebugEnabled()){
			log.debug("result:" + result);
			if(result != null){
				log.debug("result.size():" + result.size());
			}
		}
		return result;
	}
	
	public int addApply(Apply apply, String ip){
		apply.setIp(ip);
		apply.setTime(TimeUtil.getCurrentTime());
		applyDao.save(apply);
		return 1;
	}
	
	
}
