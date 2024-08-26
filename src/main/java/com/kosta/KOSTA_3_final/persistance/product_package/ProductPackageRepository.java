package com.kosta.KOSTA_3_final.persistance.product_package;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kosta.KOSTA_3_final.model.product_package.Product;
import com.kosta.KOSTA_3_final.model.product_package.Product_Package;

public interface ProductPackageRepository extends CrudRepository<Product_Package, Integer>{
	List<Product_Package> findByProduct(Product product);
	
	@Query(value ="select b.price from tp_productpack a left outer join tp_product b on(b.product_id = a.product_id) where package_id =:pid", nativeQuery = true)
	public List<Integer> findbyPackageId(@Param("pid") Long long1);
	
	@Query(value ="select n.product_name from tp_package p left outer join tp_productpack d on (p.package_id=d.package_id)"
	         + "join tp_product n on (d.product_id=n.product_id) where p.package_id= :pno", nativeQuery = true)
	public List<String> findProductbyPackageNo(@Param("pno") long pno);
}
