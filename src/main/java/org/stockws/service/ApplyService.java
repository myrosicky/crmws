package org.stockws.service;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.vo.QueryVO;

public interface ApplyService {

	public List<Apply> query(QueryVO<Apply> queryVo);
	
	public int add(Apply apply);
	
	public int update(Apply apply);

}
