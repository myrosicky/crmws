package org.stockws.dao;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplyDao extends JpaRepository<Apply, Long> {

	public List<Apply> findByArea(String area, Pageable pageable);
	public List<Apply> findByAreaAndCountry(String area, String country, Pageable pageable);
	public List<Apply> findByAreaAndCountryAndCity(String area, String country, String city, Pageable pageable);
	public List<Apply> findByAreaAndCountryAndProvince(String area,String country, String province, Pageable pageable);
	public List<Apply> findByAreaAndCountryAndProvinceAndCity(String area, String country, String province, String city, Pageable pageable);
	
    long countById(Long id);
}
