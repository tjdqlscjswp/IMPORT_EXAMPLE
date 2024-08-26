package com.kosta.KOSTA_3_final.service.subscribe;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.KOSTA_3_final.model.product_package.PackageVO;
import com.kosta.KOSTA_3_final.model.subscribe.Subscribe;
import com.kosta.KOSTA_3_final.model.user.Member;
import com.kosta.KOSTA_3_final.persistance.product_package.PackageRepository;
import com.kosta.KOSTA_3_final.persistance.subscribe.SubscribeRepository;
import com.kosta.KOSTA_3_final.persistance.user.MemberRepository;

@Service
public class SubscribeService {
	@Autowired
	MemberRepository memberRepo;
	@Autowired
	PackageRepository packRepo;
	@Autowired
	SubscribeRepository subRepo;
	public void insertSubscribe(long package_id, long customer_id ){
		PackageVO pack=   packRepo.findById(package_id).get();
		Member member = memberRepo.findById(customer_id).get();
		Subscribe sub = Subscribe.builder()
				.subscribeId(new Date().getTime())
				.pack(pack)
				.customer(member)
				.build();
		subRepo.save(sub);
	}
	
	
	public PackageVO findById(Long packageId) {
	
		return packRepo.findById(packageId).get();
	}

	public List<Subscribe> findByCustomer(Member customer) {
	      return subRepo.findAllByCustomer(customer);
	   }

	
	
}
