package org.stockws.service.impl;

import java.util.Date;
import java.util.List;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.vo.QueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.stockws.dao.ApplyDao;
import org.stockws.service.ApplyService;

import com.google.api.client.util.Strings;

@Service
public class ApplyServiceImpl implements ApplyService {

	private static final Logger log = LoggerFactory.getLogger(ApplyServiceImpl.class);
	
	@Autowired
	private ApplyDao applyDao;
	
	@Value("${custom.datasource.defaultSize}")
	private Integer defaultSize;
	
	@Override
	public List<Apply> queryMulti(QueryVO<List<Apply>> queryVo){
		log.debug("into queryMulti");
		return null;
	}
	
	@Override
	public List<Apply> query(QueryVO<Apply> queryVo){
		log.debug("into query");
		Apply apply = queryVo.getModel();
		List<Apply> result = null;
		int page = 1;
		if(queryVo.getPage() != null && queryVo.getPage() > 0){
			page = queryVo.getPage();
		}
		int size = defaultSize;
		if(queryVo.getSize() != null && queryVo.getSize() > 0){
			size = queryVo.getSize();
		}
		
		PageRequest pageReq = new PageRequest(page, size, Sort.Direction.ASC, "createTime");
		if(!Strings.isNullOrEmpty(apply.getArea()) ){
			if(!Strings.isNullOrEmpty(apply.getCountry())){
				if(!Strings.isNullOrEmpty(apply.getProvince())){
					if(!Strings.isNullOrEmpty(apply.getCity())){
						result = applyDao.findByAreaAndCountryAndProvinceAndCity(apply.getArea(), apply.getCountry(), apply.getProvince(), apply.getCity(), pageReq);
					}else{
						result = applyDao.findByAreaAndCountryAndProvince(apply.getArea(), apply.getCountry(), apply.getProvince(), pageReq);
					}
				}else{
					result = applyDao.findByAreaAndCountry(apply.getArea(), apply.getCountry(), pageReq);
				}
			}else{
				result = applyDao.findByArea(apply.getArea(), pageReq);
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
