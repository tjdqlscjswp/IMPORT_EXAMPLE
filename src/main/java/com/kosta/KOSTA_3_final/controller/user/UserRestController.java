package com.kosta.KOSTA_3_final.controller.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.KOSTA_3_final.model.user.EmailDTO;
import com.kosta.KOSTA_3_final.model.user.Member;
import com.kosta.KOSTA_3_final.service.user.UserService;

@RestController
public class UserRestController {

	
	
	@Autowired
	private JavaMailSender sender;
	
	@PostMapping("/auth/CheckMail")
	public Map<String, Object> SendMail(String email, HttpSession session) {
		//무작위로 된 인증 문자 만들기
		Map<String, Object> map = new HashMap<>();
		Random random = new Random();
		String key = "";
		//메일 보낼 것 구성
		MimeMessage message=sender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		try {
			helper.setTo(email);
			// 스크립트에서 보낸 메일을 받을 사용자 이메일 주소
			// 입력 키를 위한 코드
			for (int i = 0; i < 3; i++) {
				int index = random.nextInt(25) + 65; // A~Z까지 랜덤 알파벳 생성
				key += (char) index;
			}
			
			int numIndex = random.nextInt(8999) + 1000; // 4자리 정수를 생성
			key += numIndex;
			
			helper.setSubject("인증번호 입력을 위한 메일 전송");
			message.setText("인증번호:"+key);
			helper.setFrom("swk8632@gmail.com");
			
			sender.send(message);
			map.put("key", key);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			System.out.println("전송 실패");
			e.getMessage();
		} 
		return map;
	}
	
	@Autowired
	UserService service;
	
	@PostMapping("/auth/passwordFind")
	public @ResponseBody Map<String, Boolean> pw_find(String email, String customer_name){
	   //이메일 유무 확인 후 리턴
		Map<String,Boolean> json = new HashMap<>();
	    System.out.println(email);
	    boolean pwFindCheck = service.userEmailCheck(email, customer_name);

	    System.out.println(pwFindCheck);
	    json.put("check", pwFindCheck);
	    return json;
	}

	@PostMapping("/auth/passwordFind/sendmail")
		public @ResponseBody void sendEmail(String email){
		//임시 비밀 번호 생성
		 EmailDTO dto = service.createMailAndChangePassword(email);
		 //메세지 생성
	     MimeMessage msg=sender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(msg);
			try {
				//이메일 형식 구성
				helper.setSubject(dto.getTitle());//제목
				helper.setText(dto.getMessage());//본문
				helper.setTo(email);//받는 주소
				helper.setFrom("swk8632@gmail.com");//보내는 주소
			 sender.send(msg);//이메일 전송
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	       
	}
	
	@GetMapping("/auth/emailcheck")
	public boolean emailchk(String email) {
		return service.emailchk(email);
	}
}

