package org.stockws.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.stockws.model.Apply;

public interface ApplyDao extends CrudRepository<Apply, Integer> {

	public List<Apply> findByCountry(String country);
	public List<Apply> findByCountryAndArea(String country, String area);
	public List<Apply> findByCountryAndAreaAndCity(String country, String area, String city);
	
	
	@Query("SELECT count(p) as tag_count from Apply p " +
            "WHERE p.id = :id ")
    List<Object[]> countApplysById(@Param("id") Integer id);
	
}
