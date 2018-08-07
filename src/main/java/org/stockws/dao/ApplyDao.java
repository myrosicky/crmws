package org.stockws.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.stockws.model.Apply;

public interface ApplyDao extends CrudRepository<Apply, Integer> {

	public List<Apply> findByArea(String area);
	public List<Apply> findByAreaAndCountry(String area, String country);
	public List<Apply> findByAreaAndCountryAndCity(String area, String country, String city);
	
	
	@Query("SELECT count(p) as tag_count from Apply p " +
            "WHERE p.id = :id ")
    List<Object[]> countApplysById(@Param("id") Integer id);
	
}
