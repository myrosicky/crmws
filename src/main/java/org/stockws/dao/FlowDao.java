package org.stockws.dao;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.flow.ApplyFlow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowDao extends JpaRepository<ApplyFlow, Long>  {

	public long countByApplyIDAndStepIn(long applyId, List<String> step);
	
}
