package org.stockws.dao;

import org.business.models.applysystem.Approve;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ApproveDao extends JpaRepository<Approve, Long> {

	@EntityGraph(attributePaths = { "apply" })
	public Approve findWithApplyById(long id);
	
}
