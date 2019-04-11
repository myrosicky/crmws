package org.stockws.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.business.exceptions.AppException;
import org.business.exceptions.ApplyNotFoundException;
import org.business.exceptions.BusinessCheckingException;
import org.business.exceptions.IllegalOperationException;
import org.business.models.applysystem.Apply;
import org.business.models.applysystem.Approve;
import org.business.models.applysystem.Dictionary;
import org.business.models.applysystem.flow.ApplyFlow;
import org.business.models.applysystem.vo.QueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.stockws.dao.ApplyDao;
import org.stockws.dao.ApproveDao;
import org.stockws.dao.FlowDao;
import org.stockws.service.ApplyService;
import org.stockws.service.FlowService;
import org.stockws.util.TimeUtil;

import redis.clients.jedis.Jedis;

@Service
public class ApplyServiceImpl implements ApplyService {

	private static final Logger log = LoggerFactory.getLogger(ApplyServiceImpl.class);
	
	@Autowired
	private ApplyDao applyDao;
	
	@Autowired
	private ApproveDao approveDao;
	
	@Autowired
	private FlowService flowService;
	
	@Value("${custom.datasource.defaultSize}")
	private Integer defaultSize;
	
	@Autowired
	private FlowDao flowDao;
	
	@Autowired
	private Jedis jedis;
	
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
		if(StringUtils.isNotBlank(apply.getArea()) ){
			if(StringUtils.isNotBlank(apply.getCountry())){
				if(StringUtils.isNotBlank(apply.getProvince())){
					if(StringUtils.isNotBlank(apply.getCity())){
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
	
	public int submitForApprove(Apply apply){
		long applyID = apply.getId();
		jedis.setnx("submitForApprove." + applyID, "1");
		log.info("submit [" + applyID + "] for approval start");
		if(!applyDao.existsById(applyID)){
			throw new ApplyNotFoundException();
		}
		
		if(flowDao.countByApplyIDAndStepIn(applyID, Arrays.asList(Dictionary.FlowStep.PEND_APPROVE.toString())) > 0){
			throw new IllegalOperationException();
		}
		
		ApplyFlow flow = new ApplyFlow();
		flow.setApplyID(applyID);
		flow.setStep(Dictionary.FlowStep.PEND_APPROVE.toString());
		flow.setTime(TimeUtil.getCurrentTime());
		flowDao.save(flow);
		
		log.info("submit [" + applyID + "] for approval done");
		jedis.del("submitForApprove." + applyID);
		return 1;
	}
	
	@Override
	public int save(Apply apply, String ip, String userID) {
		long applyID = apply.getId();
		boolean applyIDExists =  StringUtils.isNotBlank(String.valueOf(applyID));
		log.info("save apply[id:" + applyID + "] start");
		if(applyIDExists){
			jedis.setnx("submitForApprove." + applyID, "1");
		}
		// checking for update
		if(applyIDExists && !applyDao.existsById(applyID)){
			throw new ApplyNotFoundException();
		}
		
		if(pending(apply)){
			throw new AppException("2");
		}
		
		if(StringUtils.isBlank(apply.getArea()) 
				||  StringUtils.isBlank(apply.getCity())
				||  StringUtils.isBlank(apply.getCountry())
				||  StringUtils.isBlank(apply.getImage())
				||  StringUtils.isBlank(apply.getName())
				||  StringUtils.isBlank(apply.getNumber())
				){
			throw new BusinessCheckingException("");
		}
			
		
		if(!applyIDExists){
			apply.setCreateBy(userID);
			apply.setCreateTime(TimeUtil.getCurrentTime());
		}
		apply.setIp(ip);
		apply.setUpdateBy(userID);
		apply.setUpdateTime(TimeUtil.getCurrentTime());
		apply.setDeleted(Dictionary.Deleted.FALSE.toString());
		applyDao.save(apply);
		log.info("save apply[id:" + apply.getId() + "] done");
		if(applyIDExists){
			jedis.del("submitForApprove." + applyID);
		}
		return 1;
	}
	
	@Override
	public int delete(Apply apply) {
		if(!applyDao.existsById(apply.getId())){
			throw new ApplyNotFoundException();
		}
		if(pending(apply)){
			throw new AppException("2");
		}
		
		apply.setDeleted(Dictionary.Deleted.TRUE.toString());
		apply.setUpdateTime(TimeUtil.getCurrentTime());
		applyDao.save(apply);
		return 1;
	}
	
	private boolean pending(Apply apply){
		return flowDao.countByApplyIDAndStepIn(apply.getId(), 
					Arrays.asList(Dictionary.FlowStep.PEND_ACCEPT.toString(), Dictionary.FlowStep.PEND_APPROVE.toString(), Dictionary.FlowStep.PEND_REVIEW.toString()))
				> 0;
	}
	
	@Override
	public int approve(Approve approve) throws AppException {
		
		// checking
		if(!applyDao.existsById(approve.getApplyID())){
			throw new ApplyNotFoundException();
		}
		
		if(approve.getId() != 0l && !approveDao.existsById(approve.getId())) {
			throw new AppException("3");
		}
		
		approve.setType(Dictionary.ApproveType.APPROVE.toString());
		approve.setTime(TimeUtil.getCurrentTime());
		approveDao.save(approve);
		
		if(!Dictionary.ApproveResult.SAVE.toString().equals(approve.getResult())){
			ApplyFlow flow = new ApplyFlow();
			flow.setApplyID(approve.getApplyID());
			if(Dictionary.ApproveResult.FAIL.toString().equals(approve.getResult())){
				flow.setStep(Dictionary.FlowStep.FAILURE.toString());
			}else if(Dictionary.ApproveResult.PASS.toString().equals(approve.getResult())){
				flow.setStep(Dictionary.FlowStep.APPROVED.toString());
			}
			flowService.insertFlow(flow);
		}
		return 1;
	}

	@Override
	public int review(Approve approve) throws AppException {
		// checking
		if(!applyDao.existsById(approve.getApplyID())){
			throw  new ApplyNotFoundException();
		}
		
		if(approve.getId() != 0l && !approveDao.existsById(approve.getId())) {
			throw new AppException("3");
		}
		approve.setType(Dictionary.ApproveType.REVIEW.toString());
		approve.setTime(TimeUtil.getCurrentTime());
		approveDao.save(approve);
		
		if(!Dictionary.ApproveResult.SAVE.toString().equals(approve.getResult())){
			ApplyFlow flow = new ApplyFlow();
			flow.setApplyID(approve.getApplyID());
			if(Dictionary.ApproveResult.FAIL.toString().equals(approve.getResult())){
				flow.setStep(Dictionary.FlowStep.FAILURE.toString());
			}else if(Dictionary.ApproveResult.PASS.toString().equals(approve.getResult())){
				flow.setStep(Dictionary.FlowStep.REVIEWED.toString());
			}
			flowService.insertFlow(flow);
		}
				
		return 0;
	}

	@Override
	public int returnBack(Approve approve) throws AppException {
		// checking
		if(!applyDao.existsById(approve.getApplyID())){
			throw  new ApplyNotFoundException();
		}
		
		if(approve.getId() != 0l && !approveDao.existsById(approve.getId())) {
			throw new AppException("3");
		}
		approve.setType(null);
		approve.setTime(TimeUtil.getCurrentTime());
		approveDao.save(approve);
		
		ApplyFlow flow = new ApplyFlow();
		
		flow.setApplyID(approve.getApplyID());
		flow.setStep(Dictionary.FlowStep.CREATE.toString());
		flowService.insertFlow(flow);
				
		return 0;
	}
	
	
	
	
	
	
}
