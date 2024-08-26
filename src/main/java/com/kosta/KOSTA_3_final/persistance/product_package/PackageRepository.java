package com.kosta.KOSTA_3_final.persistance.product_package;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kosta.KOSTA_3_final.model.product_package.PackageVO;


public interface PackageRepository extends CrudRepository<PackageVO, Long>{
	@Query("select p from PackageVO p where rownum <=6 and packageType=?1")
	public List<PackageVO> findByPackageType(int packageType);
	
	@Query("select p from PackageVO p where rownum <=5 and packageType=?1")
	public List<PackageVO> findByPackageType3(int packageType);
	
	
	@Query("select img from PackageVO")
	public List<Integer> getImgName();
	
	@Query("select p from PackageVO p where rownum <=8 and packageType=?1")
	public List<PackageVO> findByPackageType2(int packageType);
}
