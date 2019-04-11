package org.stockws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.business.models.Menu;
import org.business.models.MenuRole;
import org.business.models.UserRole;
import org.business.models.applysystem.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stockws.dao.MenuDao;
import org.stockws.dao.MenuRoleDao;
import org.stockws.dao.UserRoleDao;
import org.stockws.service.MenuService;
import org.stockws.util.TimeUtil;

@Service
public class MenuServiceImpl implements MenuService {

	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuRoleDao menuRoleDao;

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private UserRoleDao userRoleDao;
	
	
	@Override
	public int saveMenu(Menu menu, long userId){
		menu.setDeleted(Dictionary.Deleted.FALSE.toString());
		menu.setCreateTime(TimeUtil.getCurrentTime());
		menu.setCreateBy(userId);
		menuDao.save(menu);
		return 1;
	}
	
	@Override
	public int saveMenuRole(MenuRole menuRole, long userId){
		menuRole.setDeleted(Dictionary.Deleted.FALSE.toString());
		menuRole.setCreateTime(TimeUtil.getCurrentTime());
		menuRole.setCreateBy(userId);
		menuRoleDao.save(menuRole);
		return 1;
	}
	
	
	/* (non-Javadoc)
	 * @see org.stockws.service.impl.MenuService#getMenu(long)
	 */
	@Override
	public List<Menu> getMenu(long userId){
		if(log.isDebugEnabled()){
			log.debug("getMenu for userId:" + userId);
		}
		List<UserRole> userRoles = userRoleDao.findByOwnerId(userId);
		List<Long> roles = new ArrayList<>(userRoles.size());
		for(UserRole role : userRoles){
			roles.add(role.getRoleId());
		}
		List<MenuRole> menuRoles = menuRoleDao.findByUserIdOrRoleIdIn(userId, roles);
		List<Menu> result = new ArrayList<>(menuRoles.size());
		for(MenuRole role : menuRoles){
			result.add(role.getMenu());
		}
		if(log.isDebugEnabled()){
			log.debug("getMenu result:" + result);
		}
		return result;
	}
}
