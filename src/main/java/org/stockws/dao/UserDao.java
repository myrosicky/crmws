package org.stockws.dao;

import java.util.concurrent.CopyOnWriteArrayList;

import org.business.models.User;
import org.business.models.UserRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {

	CopyOnWriteArrayList<User> findByUsernameContaining(String term);

	User findByUsername(String username);
	
//	@EntityGraph(value = "userRole.fetchUserAndRole" )
//	UserRole findByUsername(String userName);

}
