package org.stockws.dao;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplyDao extends JpaRepository<Apply, Long> {

	public List<Apply> findByArea(String area);
	public List<Apply> findByAreaAndCountry(String area, String country);
	public List<Apply> findByAreaAndCountryAndCity(String area, String country, String city);
	public List<Apply> findByAreaAndCountryAndProvince(String area,String country, String province);
	public List<Apply> findByAreaAndCountryAndProvinceAndCity(String area, String country, String province, String city);
	
	@Query("SELECT count(p) as tag_count from Apply p WHERE p.id = :id ")
    List<Object[]> countApplysById(@Param("id") Long id);
}
