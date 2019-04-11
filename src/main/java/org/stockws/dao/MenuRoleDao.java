package org.stockws.dao;

import java.util.List;

import org.business.models.MenuRole;
import org.business.models.UserRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface MenuRoleDao extends CrudRepository<MenuRole, Long> {

	@EntityGraph(value = "menuRole.fetchMenu" )
	List<MenuRole> findByUserIdOrRoleIdIn(Long ownerId, List<Long> roleIds);
	
}
