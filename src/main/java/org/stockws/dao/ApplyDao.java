package org.stockws.dao;

import java.util.List;

import org.business.models.applysystem.Apply;
import org.business.models.applysystem.report.MthApplyRpt;
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
	
	@Query("	select substr(a.createTime, 0, 7) createMonth, "
			+ "    count(*) total, "
			+ "    coalesce(sum(1), 0) apply,"
			+ "	   0 pendApprove,"
			+ "	   coalesce(sum((select 1 from Approve ap where ap.applyID=a.id and ap.type='1' and ap.result='2')), 0) approved,"
			+ "    0 pendReview, " 
			+ "    coalesce(sum((select 1 from Approve ap where ap.applyID=a.id and ap.type='2' and ap.result='2')), 0) reviewed, "  
			+ "    coalesce(sum((select 1 from Approve ap where ap.applyID=a.id and ap.type='1' and ap.result='2')), 0) completed, " 
			+ "    coalesce(sum((select 1 from Approve ap where ap.applyID=a.id and ap.result='3')), 0) fail, "      
			+ "    0 returnBack"
			+ " from Apply a "
			+ " where a.createTime > :createTimeBgin and a.createTime < :createTimeEnd "
			+ "       and a.deleted=:deleted"
			+ " group by substr(a.createTime, 1, 7)")
	List<MthApplyRpt> monthCntByCreateTimeLikeAndDeleted(@Param("createTimeBgin") String createTimeBgin, @Param("createTimeEnd") String createTimeEnd, @Param("deleted") String deleted);
	
	
    long countById(Long id);
}
