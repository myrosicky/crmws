package org.stockws.service.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.business.models.User;
import org.business.models.UserRole;
import org.business.models.applysystem.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stockws.dao.UserDao;
import org.stockws.service.UserOpsService;
import org.stockws.util.CipherUtil;
import org.stockws.util.TimeUtil;

import com.google.common.base.Optional;

@Service
public class UserOpsServiceImpl implements UserOpsService {

	private final static Logger log = LoggerFactory.getLogger(UserOpsServiceImpl.class);

	@Autowired
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see service.UserOpsService#findByUsername(java.lang.String)
	 */
	@Override
	public User findByUsername(String username) {
		 User user = userDao.findByUsername(username);
		 return user;
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#saveUser(po.CrmUser)
	 */
	@Override
	public void saveUser(User user) {
		userDao.save(user);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#findByUsernameContaining(java.lang.String)
	 */
	@Override
	public CopyOnWriteArrayList<User> findByUsernameContaining(String term) {
		return userDao.findByUsernameContaining(term);
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#checkUser(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<User> checkUser(String username, String password) {
		User user = findByUsername(username);
		return (Optional<User>) (!CipherUtil.MD5Match(password, user.getPassword())?Optional.absent():Optional.of(user));
	}

	/* (non-Javadoc)
	 * @see service.UserOpsService#saveUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void saveUser(String username, String password, String remoteHost) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(CipherUtil.BCryptEncode(password));
		user.setGeneral_ip(remoteHost);
		user.setRegisterDate(TimeUtil.getCurrentTime());
		user.setDeleted(Dictionary.Deleted.FALSE.toString());
		saveUser(user);
	}

}
