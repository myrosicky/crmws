package org.stockws.dao.iface;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.stockws.po.CrmUser;
import org.stockws.util.NamingUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface UserDao {

	public abstract CopyOnWriteArrayList<CrmUser> findByUsernameContaining(String term);

	public abstract void saveUser(CrmUser user);

	public abstract CrmUser findByUsername(String username);

}
