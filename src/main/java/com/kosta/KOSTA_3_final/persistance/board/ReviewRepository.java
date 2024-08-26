package com.kosta.KOSTA_3_final.persistance.board;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.kosta.KOSTA_3_final.model.board.QReview;
import com.kosta.KOSTA_3_final.model.board.Review;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface ReviewRepository extends CrudRepository<Review, Long>, QuerydslPredicateExecutor<Review>{

	 public default Predicate makePredicate(String type, String keyword) {
			BooleanBuilder builder = new BooleanBuilder();
			
			QReview review = QReview.review1;
			 
			if(type==null) return builder;
			builder.and(review.reviewId.gt(0L));// and board_id>0
			String whereStr = "%"+keyword+"%";
			switch(type) {
			case "pack":
				builder.and(review.pack.packageName.like(whereStr));
				break;
			
			case "email":
				builder.and(review.customer.email.like(whereStr));
				break;
			default:
				break;
			}
			 
			return builder;
		}

	 @Query(value = "select AVG(SCORE) from ONELINEBOARD where PACKAGE_ID = packageId" , nativeQuery = true)
	 public List<Integer> scoreAvg(Long packageId);
}
