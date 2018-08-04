package org.stockws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.stockws.dao.ApplyDao;
import org.stockws.model.Apply;
import org.stockws.service.ApplyService;

import com.google.api.client.util.Strings;

@Service
public class ApplyServiceImpl implements ApplyService {

	@Autowired
	private ApplyDao applyDao;
	
	@Override
	public List<Apply> query(String country, String area, String city){
		Apply input = new Apply();
		input.setCountry(country);
		input.setArea(area);
		input.setCity(city);
		
		List<Apply> result = null;
		if(!Strings.isNullOrEmpty(input.getCountry()) ){
			if(!Strings.isNullOrEmpty(input.getArea())){
				if(!Strings.isNullOrEmpty(input.getCity())){
					applyDao.findByCountryAndAreaAndCity(input.getCountry(), input.getArea(), input.getCity());
				}else{
					result = applyDao.findByCountryAndArea(input.getCountry(), input.getArea());
				}
			}else{
				result = applyDao.findByCountry(input.getCountry());
			}
		}
		
		return result;
	}
	
	
	
}
