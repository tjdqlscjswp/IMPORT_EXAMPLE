package com.kosta.KOSTA_3_final.persistance.subscribe;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.kosta.KOSTA_3_final.model.subscribe.QSubscribe;
import com.kosta.KOSTA_3_final.model.subscribe.Subscribe;
import com.kosta.KOSTA_3_final.model.user.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface SubscribeRepository extends CrudRepository<Subscribe, Long>, QuerydslPredicateExecutor<Subscribe> {
	
	
	  public default Predicate makePredicate(String type, String keyword) {
	  BooleanBuilder builder = new BooleanBuilder(); 
	  QSubscribe subscribes = QSubscribe.subscribe;
	  builder.and(subscribes.subscribeId.gt(0));
	  
	  return builder; 
	  }
	  public List<Subscribe> findAllByCustomer(Member customer);
	  

}
