package org.stockws.dao;

import java.util.List;

import org.business.models.UserRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleDao extends CrudRepository<UserRole, Long> {

	List<UserRole> findByOwnerId(Long ownerId);
	
	@EntityGraph(value = "userRole.fetchUserAndRole" )
	UserRole findByUserId(Long userId);

}
