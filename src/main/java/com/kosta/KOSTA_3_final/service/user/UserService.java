package com.kosta.KOSTA_3_final.service.user;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kosta.KOSTA_3_final.model.user.EmailDTO;
import com.kosta.KOSTA_3_final.model.user.Member;
import com.kosta.KOSTA_3_final.persistance.user.MemberRepository;
import com.kosta.KOSTA_3_final.security.UserSecurity;




@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	MemberRepository repo;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired 
	JavaMailSender sender;
	
	//회원 가입
	@Transactional
	public Member joinUser(Member member) {
		// 비밀번호 암호화...암호화되지않으면 로그인되지않는다.
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		// member.setMrole(MemberRoleEnumType.USER);
		System.out.println(passwordEncoder.encode(member.getPassword()));
		return repo.save(member);
	}	
	
	public Member getMemberInfo(String email) {
		return repo.findByEmail(email).get();
	}
	
	public Member getMemberInfoById(int customerId) {
		return repo.findById((long) customerId).get();
		
	}
	

	
	 public EmailDTO createMailAndChangePassword(String email){
	     //메일 본문 생성 및 비번 바꾸기   
		 String str = getTempPassword();
	        EmailDTO dto = new EmailDTO();
	        String rr=repo.findByEmail(email).get().getCustomerName();
	        System.out.println(rr);
	        dto.setAddress(email);
	        dto.setTitle(rr+"님의  임시비밀번호 안내 이메일 입니다.");
	        dto.setMessage("안녕하세요.  임시비밀번호 안내 관련 이메일 입니다." + "[" + rr + "]" +"님의 임시 비밀번호는 "
	        + str + " 입니다.");
	        System.out.println(dto);
	        updatePassword(str,email);
	        return dto;
	    }
	
	 public String getTempPassword(){
		 //랜덤한 문자와 숫자 배열 생성
	        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
	                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	        String str = "";

	        int idx = 0;
	        for (int i = 0; i < 10; i++) {
	            idx = (int) (charSet.length * Math.random());
	            str += charSet[idx];
	        }
	        return str;
	    }
	 
	  public void updatePassword(String str,String userEmail){
	      //비번 암호화 및 업데이트  
		  String pw = passwordEncoder.encode(str);
	        String name=repo.findByEmail(userEmail).get().getCustomerName();
	       System.out.println(name);
	        Member id = repo.findByCustomerNameAndEmail(name, userEmail);
	       id.setPassword(pw);
	        repo.save(id);
	    }
	 
	public boolean userEmailCheck(String userEmail, String userName) {
		//이메일 유무 확인
        Member member = repo.findByEmail(userEmail).get();
        if(member!=null && member.getCustomerName().equals(userName)) {
            return true;
        }
        else {
            return false;
        }
    }
	
	public boolean emailchk(String email) {
	return  repo.existsByEmail(email);
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserDetails member=repo.findByEmail(email)
				.filter(m -> m != null).map(m -> new UserSecurity(m)).get();
		
		return member;
	}
}

