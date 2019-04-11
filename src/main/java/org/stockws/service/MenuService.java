package org.stockws.service;

import java.util.ArrayList;
import java.util.List;

import org.business.models.Menu;
import org.business.models.MenuRole;
import org.business.models.UserRole;
import org.business.models.applysystem.Dictionary;
import org.stockws.util.TimeUtil;

public interface MenuService {

	public abstract List<Menu> getMenu(long userId);

	public abstract int saveMenuRole(MenuRole menuRole, long userId);

	public abstract int saveMenu(Menu menu, long userId);

}