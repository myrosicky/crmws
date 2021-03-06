package org.stockws.service.impl;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.report.MthApplyRpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stockws.dao.ApplyDao;

@Service
public class ReportServiceImpl {

	private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	@Autowired
	private ApplyDao applyDao;
	
	
	public MthApplyRpt[] summaryMonthApply(String startMonth, String endMonth){
		List<MthApplyRpt> result = applyDao.monthCntByCreateTimeLikeAndDeleted(startMonth, endMonth, Apply.DELETED_FALSE);
		if(log.isDebugEnabled()){
			log.debug("result.size():" + result.size());
		}
		return result.toArray(new MthApplyRpt[result.size()]);
	}
	
}
