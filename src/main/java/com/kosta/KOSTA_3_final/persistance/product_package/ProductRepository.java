package com.kosta.KOSTA_3_final.persistance.product_package;


import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.kosta.KOSTA_3_final.model.product_package.Product;
import com.kosta.KOSTA_3_final.model.product_package.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface ProductRepository extends CrudRepository<Product, Integer>, QuerydslPredicateExecutor<Product>{
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QProduct product = QProduct.product;
		//builder.and(product.product_id.gt(0)); // and bno>0
		
		if(type==null) return builder;
		switch(type) {
		case "product":
			builder.and(product.productName.like("%"+keyword+"%"));
			break;
		case "category":
			builder.and(product.categoryId.like(keyword));
			break;
		case "company":
			builder.and(product.companyId.like("%"+keyword+"%"));
			break;
		default:
			break;
		}
		
		return builder;
	}
}
