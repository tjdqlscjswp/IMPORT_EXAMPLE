package com.kosta.KOSTA_3_final.controller.user;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.kosta.KOSTA_3_final.model.user.Member;
import com.kosta.KOSTA_3_final.persistance.product_package.PackageRepository;
import com.kosta.KOSTA_3_final.security.UserSecurity;
import com.kosta.KOSTA_3_final.service.subscribe.SubscribeService;
import com.kosta.KOSTA_3_final.service.user.UserService;

@Controller
public class UserController {
	@Autowired
	UserService service;
	
	@Autowired
	JavaMailSender sender;
	
	@Autowired
	SubscribeService sub;
	
	@Autowired
	PackageRepository pack;
	
	
	@GetMapping("/login")
	public String login(){
		AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
		if (trustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
		// 비로그인시
			return "login";
		}
		else { 
		// 로그인한 사용자 
			return "redirect:/index";
		}
		
	}
	
	@GetMapping("/accessFail")
	public void deniedMethod(){
		
	}
	@GetMapping("/Home")
	public void loginMethod(){
		
	}

	@GetMapping("/logout")
	public void logout(){
		
		
	}
	@GetMapping("/auth/joinForm")
	public String joinUser(){
		AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
		if (trustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
		// 비로그인시
			return "auth/joinForm";
		}
		else { 
		// 로그인한 사용자 
			return "redirect:/index";
		}
		
	}
	
	@PostMapping("/auth/joinForm")
	public String joinUser2(@ModelAttribute Member user) {
		
		service.joinUser(user);//회원 가입 후 로그인 페이지로 리턴
		
		return "redirect:/login";
		
		}
		
	
	
@GetMapping("/auth/passwordFind")
public void pwdfind() {
	
}

@GetMapping("/mypage/profile")
public String profile(Model model) {
	Object princifal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();//로그인시 정보 받아오기
	UserSecurity ss=(UserSecurity)princifal;
	Member mem=service.getMemberInfo(ss.getUsername());//정보 찾기
	model.addAttribute("memberinfo", mem);//저장 후 마이페이지 내에 출력
	
	return "mypage/profile";
}
@GetMapping("/mypage/edit")
public String profileEdit(Model model) {
	Object princifal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();//로그인시 정보 받아오기
	UserSecurity ss=(UserSecurity)princifal;
	Member mem=service.getMemberInfo(ss.getUsername());//정보 찾기
	model.addAttribute("memberinfo", mem);//데이터로 저장해서 정보 수정 내에 출력
	
	return "mypage/edit";
}
@PostMapping("/mypage/edit")
public String profileAfterEdit(Model model,Member member) {
	//회원 정보 수정
	Member mem=service.getMemberInfo(member.getEmail());//이메일로 정보 받아옴
	mem.setAddress(member.getAddress());
	mem.setAddressdetail(member.getAddressdetail());
	mem.setCustomerName(member.getCustomerName());
	mem.setPassword(member.getPassword());
	mem.setPhone_number(member.getPhone_number());
	mem.setPostnumber(member.getPostnumber());//정보 수정
	service.joinUser(mem);// 저장

		Object princifal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();//이후 로그인 상태시 정보 받아오기 
		UserSecurity ss=(UserSecurity)princifal;
		Member mem2=service.getMemberInfo(ss.getUsername());
		model.addAttribute("memberinfo", mem2);//이 후 데이터 저장해서 프로필 페이지에 띄우게한다
	
	
	
	return "mypage/profile";
}





}




