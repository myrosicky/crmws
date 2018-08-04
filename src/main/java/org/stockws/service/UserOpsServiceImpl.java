package org.stockws.service;

import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stockws.dao.UserDao;
import org.stockws.model.CrmUser;
import org.stockws.service.iface.UserOpsService;
import org.stockws.util.CipherUtil;

import com.google.common.base.Optional;

@Service
public class UserOpsServiceImpl implements UserOpsService {

	private final static Logger logger = LoggerFactory.getLogger(UserOpsServiceImpl.class);

	@Autowired
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see service.UserOpsService#findByUsername(java.lang.String)
	 */
	@Override
	public CrmUser findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#saveUser(po.CrmUser)
	 */
	@Override
	public void saveUser(CrmUser user) {
		userDao.save(user);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#findByUsernameContaining(java.lang.String)
	 */
	@Override
	public CopyOnWriteArrayList<CrmUser> findByUsernameContaining(String term) {
		return userDao.findByUsernameContaining(term);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#checkUser(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<CrmUser> checkUser(String username, String password) {
		CrmUser user = findByUsername(username);
		return (Optional<CrmUser>) (!CipherUtil.MD5Match(password, user.getPassword())?Optional.absent():Optional.of(user));
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#saveUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void saveUser(String username, String password, String remoteHost) {
		CrmUser user = new CrmUser();
		user.setUsername(username);
		user.setPassword(CipherUtil.MD5(password));
		user.setGeneral_ip(remoteHost);
		saveUser(user);
	}

}
