package com.kosta.KOSTA_3_final.controller.board;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kosta.KOSTA_3_final.model.board.Board;
import com.kosta.KOSTA_3_final.model.board.PageMake;
import com.kosta.KOSTA_3_final.model.board.PageVO;
import com.kosta.KOSTA_3_final.model.user.Member;
import com.kosta.KOSTA_3_final.service.board.BoardService;
import com.kosta.KOSTA_3_final.service.user.UserService;

@Controller
public class BoardController {

	@Autowired
	BoardService service;
	@Autowired
	UserService uservice;
	
	@GetMapping("/board/boardlist")
	public void selectAll(Model model, PageVO pagevo) {
		if(pagevo==null) pagevo = PageVO.builder().page(1).size(10).keyword("").type("").build();
		System.out.println("controller...pagevo:" + pagevo);
		Page<Board> result = service.selectAll(pagevo);
		
		
	 
		model.addAttribute("boardResult", result);
		model.addAttribute("pagevo", pagevo);
		model.addAttribute("result", new PageMake<>(result));
		
	}
	
	@GetMapping("/board/register")
	public void boardRegister() {
		
	}
	
	@PostMapping("/board/register")
	public String boardRegisterPost(Board board, RedirectAttributes rttr, Principal principal) {
		System.out.println("rttr"+rttr);
		Member member = uservice.getMemberInfo(principal.getName());
		board.setCustomer(member);
		System.out.println(board);
		Board ins_board = service.insertBoard(board);
		//주소창에 보이지 않고 전달된다.
		rttr.addFlashAttribute("resultMessage", ins_board==null?"입력실패":"입력성공");
		return "redirect:/board/boardlist";
	}

	  @GetMapping("/board/boarddetail") 
	  public void selectAll(Model model, Long bid, PageVO pagevo) { 
		  model.addAttribute("board",service.selectById(bid)); 
		  model.addAttribute("pagevo", pagevo);
		  }

	  @GetMapping("/board/delete")
	  public String boardDelete(Long bid) {
		  int ret = service.deleteBoard(bid);
		  System.out.println("삭제 : "+ret);
		  return "redirect:/board/boardlist";
				 
	  }
	
	  @PostMapping("/board/update")
	  public String boardUpdate(Long bid, RedirectAttributes rttr, PageVO pagevo, 
			  String bcontent, String btitle, String email) {
		  
		  Board board = new Board();
		  board.setBid(bid);
		  board.setBcontent(bcontent);
		  board.setBtitle(btitle);
		  board.setCustomer(uservice.getMemberInfo(email));
		  Board update_board = service.updateBoard(board);
		  System.out.println("수정사항 : "+update_board);

		  rttr.addFlashAttribute("resultMessage", update_board==null?"수정실패":"수정성공");
		  rttr.addFlashAttribute("pagevo", pagevo);
		  
		  return "redirect:/board/boarddetail?bid="+bid;
				 
	  }

}
