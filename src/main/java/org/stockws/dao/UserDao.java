package org.stockws.dao;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.data.repository.CrudRepository;
import org.stockws.model.CrmUser;

public interface UserDao extends CrudRepository<CrmUser, Long> {

	public abstract CopyOnWriteArrayList<CrmUser> findByUsernameContaining(String term);

	public abstract CrmUser findByUsername(String username);

}
