package com.kosta.KOSTA_3_final.service.subscribe;


import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kosta.KOSTA_3_final.model.product_package.PackageVO;
import com.kosta.KOSTA_3_final.model.subscribe.Delivery;
import com.kosta.KOSTA_3_final.model.subscribe.PageVO;
import com.kosta.KOSTA_3_final.model.user.Member;
import com.kosta.KOSTA_3_final.persistance.product_package.PackageRepository;
import com.kosta.KOSTA_3_final.persistance.subscribe.DeliveryRepository;
import com.kosta.KOSTA_3_final.persistance.user.MemberRepository;
import com.querydsl.core.types.Predicate;

@Service
public class DeliveryService {
	
	@Autowired
	DeliveryRepository deliRepo;
	@Autowired
	PackageRepository packRepo;
	
	@Autowired
	MemberRepository memberRepo;

	public List<Delivery> selectDeliveryList(Date date) {
		System.out.println(date);
		Predicate p = deliRepo.makePredicate(date);
		return (List<Delivery>) deliRepo.findAll(p);
	}
	
	public Page<Delivery> selectDeliveryList1(PageVO pvo) {

		Predicate p = deliRepo.makePredicate(pvo.getDate());
		
		Pageable pageable = pvo.makePaging(0, "deliveryId");
		Page<Delivery> result = deliRepo.findAll(p, pageable);
		
		return result;
	}

	public void deliveryInsert(long packageId, long customerId,Date date) {

		
		PackageVO package_id = packRepo.findById(packageId).get();
		Member customer_id = memberRepo.findById(customerId).get();
		
		Delivery delivery = Delivery.builder().
		pack(package_id).
		customer(customer_id).

		deliveryDate(date).

		build();
		
		deliRepo.save(delivery);
	}
}
