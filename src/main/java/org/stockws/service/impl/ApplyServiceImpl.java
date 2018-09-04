package org.stockws.service.impl;

import java.util.Date;
import java.util.List;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.vo.QueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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
	public List<Apply> query(QueryVO<Apply> queryVo){
		Apply applu = queryVo.getModel();
		List<Apply> result = null;
		if(!Strings.isNullOrEmpty(applu.getArea()) ){
			if(!Strings.isNullOrEmpty(applu.getCountry())){
				if(!Strings.isNullOrEmpty(applu.getProvince())){
					if(!Strings.isNullOrEmpty(applu.getCity())){
						result = applyDao.findByAreaAndCountryAndProvinceAndCity(applu.getArea(), applu.getCountry(), applu.getProvince(), applu.getCity());
					}else{
						result = applyDao.findByAreaAndCountryAndProvince(applu.getArea(), applu.getCountry(), applu.getProvince());
					}
				}else{
					result = applyDao.findByAreaAndCountry(applu.getArea(), applu.getCountry());
				}
			}else{
				result = applyDao.findByArea(applu.getArea());
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
	
	@Override
	public int add(Apply apply){
		Assert.notNull(apply, "apply needed to update should not be null");
		
		apply.setCreateTime(new Date());
		applyDao.save(apply);
		return 1;
	}

	@Override
	public int update(Apply apply) {
		Assert.notNull(apply, "apply needed to update should not be null");
		Assert.isTrue(apply.getId() != -1, "no apply id found");
		
		apply.setUpdateTime(new Date());
		applyDao.save(apply);
		return 1;
	}
	
	
	
}
