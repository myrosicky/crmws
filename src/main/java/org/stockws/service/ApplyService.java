package org.stockws.service;

import java.util.List;

import org.business.exceptions.AppException;
import org.business.models.applysystem.Apply;
import org.business.models.applysystem.Approve;
import org.business.models.applysystem.vo.QueryVO;

public interface ApplyService {

	public List<Apply> query(QueryVO<Apply> queryVo) throws AppException;
	

	public List<Apply> queryMulti(QueryVO<List<Apply>> queryVo) throws AppException;

	public int  delete(Apply apply) throws AppException;

	public int approve(Approve apply) throws AppException;
	
	public int review(Approve apply) throws AppException;
	
	public int returnBack(Approve apply) throws AppException;

	int save(Apply apply, String ip, String userID) throws AppException;
	
	

}
