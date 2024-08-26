package com.kosta.KOSTA_3_final.controller.board;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.kosta.KOSTA_3_final.model.board.PageMake;
import com.kosta.KOSTA_3_final.model.board.PageVO;
import com.kosta.KOSTA_3_final.model.board.QnA;
import com.kosta.KOSTA_3_final.model.user.Member;

import com.kosta.KOSTA_3_final.service.board.QnAService;
import com.kosta.KOSTA_3_final.service.user.UserService;

@Controller
public class QnAController {
	@Autowired
	QnAService service;
	@Autowired
	UserService uservice;
	
	@GetMapping("/board/qnalist")
	public void selectAll(Model model, PageVO pagevo) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String email = ((User) principal).getUsername();
		String auth = uservice.getMemberInfo(email).getAuth().name();
		Integer id = (int) uservice.getMemberInfo(email).getCustomer_id();
		System.out.println(email+auth);
		
		
		Page<QnA> result;
		
		if(auth.equals("ADMIN")) {
			result = service.selectAll(pagevo);
		}
		
		else {
			result = service.selectByUser(pagevo, id);
		}
		
		model.addAttribute("qnaResult", result);
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("result", new PageMake<>(result));
	}
	
	
	@GetMapping("/board/qnaregister")
	public void boardRegister() {
		
	}
	
	@PostMapping("/board/qnaregister")
	public String boardRegisterPost(QnA qna, RedirectAttributes rttr, Principal principal) {
		Member member = uservice.getMemberInfo(principal.getName());
		qna.setCustomer(member);
		System.out.println(qna);
		QnA ins_qna = service.insertQnA(qna);
		//주소창에 보이지 않고 전달된다.
		rttr.addFlashAttribute("resultMessage", ins_qna==null?"입력실패":"입력성공");
		return "redirect:/board/qnalist";
	}

	  @GetMapping("/board/qnadetail") 
	  public void selectAll(Model model, Long qid, PageVO pagevo) { 
		  model.addAttribute("qna",service.selectById(qid)); 
		  model.addAttribute("pagevo", pagevo);
		  }

	  @GetMapping("/board/qnadelete")
	  public String qnaDelete(Long qid) {
		  return "redirect:/board/qnalist";
				 
	  }

}
