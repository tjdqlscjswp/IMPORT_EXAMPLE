package com.kosta.KOSTA_3_final.persistance.subscribe;

import java.sql.Date;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.kosta.KOSTA_3_final.model.subscribe.Delivery;
import com.kosta.KOSTA_3_final.model.subscribe.QDelivery;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface DeliveryRepository extends CrudRepository<Delivery, Long>, QuerydslPredicateExecutor<Delivery>{
	public default Predicate makePredicate(Date date) {
		BooleanBuilder builder = new BooleanBuilder();
		QDelivery delivery = QDelivery.delivery;

//		Date date = Date.valueOf("2021/02/11");
		builder.and(delivery.deliveryId.gt(0L));
		builder.and(delivery.deliveryDate.eq(date));
			
		return builder;
	}
}
