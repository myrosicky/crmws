package org.stockws.dao;

import java.util.List;

import org.business.models.Menu;
import org.business.models.MenuRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDao extends JpaRepository<Menu, Long>  {

}
