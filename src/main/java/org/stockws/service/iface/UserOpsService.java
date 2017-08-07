package org.stockws.service.iface;

import java.util.concurrent.CopyOnWriteArrayList;

import org.stockws.po.CrmUser;

import com.google.common.base.Optional;

public interface UserOpsService {

	public CrmUser findByUsername(String username);

	public void saveUser(CrmUser user);

	public CopyOnWriteArrayList<CrmUser> findByUsernameContaining(String term);

	public Optional<CrmUser> checkUser(String username, String password);

	public void saveUser(String username, String password, String remoteHost);

}