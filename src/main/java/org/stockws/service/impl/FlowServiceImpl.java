package org.stockws.service.impl;

import java.util.Arrays;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.Approve;
import org.business.models.applysystem.flow.ApplyFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stockws.dao.FlowDao;
import org.stockws.service.FlowService;
import org.stockws.util.TimeUtil;

@Service
public class FlowServiceImpl implements FlowService {

	private static final Logger log = LoggerFactory.getLogger(FlowServiceImpl.class);
	
	@Autowired
	private FlowDao flowDao;
	
	
	/* (non-Javadoc)
	 * @see org.stockws.service.impl.FlowService#insertFlow(org.business.models.applysystem.flow.ApplyFlow)
	 */
	@Override
	public void insertFlow(ApplyFlow flow){
		flow.setTime(TimeUtil.getCurrentTime());
		flowDao.save(flow);
	}
	
	
}
