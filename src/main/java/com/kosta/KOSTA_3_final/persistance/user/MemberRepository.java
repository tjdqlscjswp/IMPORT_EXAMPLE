package com.kosta.KOSTA_3_final.persistance.user;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kosta.KOSTA_3_final.model.user.Member;
public interface MemberRepository extends CrudRepository<Member, Long>{
   public Optional<Member> findByEmail(String email);
   public Member findByCustomerName(String userEmail);
   public Member findByCustomerNameAndEmail(String name,String email);
	 public boolean existsByEmail(String email);
	
}

