package com.kosta.KOSTA_3_final.controller.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.KOSTA_3_final.model.board.Board;
import com.kosta.KOSTA_3_final.model.board.BoardReply;
import com.kosta.KOSTA_3_final.persistance.board.BoardReplyPersistance;
import com.kosta.KOSTA_3_final.service.board.BoardReplyService;
import com.kosta.KOSTA_3_final.service.board.BoardService;
import com.kosta.KOSTA_3_final.service.user.UserService;


@RestController
@RequestMapping("/replies/*")
public class BoardReplyController {

	@Autowired
	BoardReplyService service;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardReplyPersistance persistance;
	
	@Autowired
	UserService userService;
	
	//특정 보드 번호에 해당하는 모든 댓글 조회
	@GetMapping("/board/{bid}")
	public ResponseEntity<List<BoardReply>> selectAll(@PathVariable Long bid) {
		Board board = Board.builder().bid(bid).build();
		return new ResponseEntity<>(service.selectAll(board), HttpStatus.OK);
	}
	
	//댓글상세보기
		@GetMapping("{rid}")
		public ResponseEntity<BoardReply> viewReply(@PathVariable Long rid) {
				return new ResponseEntity<>(service.selectById(rid), HttpStatus.OK);
			}
	
	
	
	//특정 보드에 댓글등록, 입려 후 다시 조회
	@PostMapping("/{bid}")
	public ResponseEntity<List<BoardReply>> addReply(@PathVariable("bid")Long bid, BoardReply reply, String email) {
		System.out.println("e:"+email);
		
		Board board = Board.builder().bid(bid).build();
		reply.setBoard(board);
		reply.setCustomer(userService.getMemberInfo(email));
		service.updateOrInsert(reply);
		return new ResponseEntity<>(service.selectAll(board), HttpStatus.CREATED);
	}
	
		
	
	
	//삭제
	@DeleteMapping("/{bid}/{rid}")
	public ResponseEntity<List<BoardReply>> deleteByrid(@PathVariable Long rid, @PathVariable Long bid) {
		service.delete(rid);
		Board board = Board.builder().bid(bid).build();
		
		return new ResponseEntity<>(service.selectAll(board), HttpStatus.OK);
	}
	
	//수정  {bid: "48", reply: "fewafe", customer: "25", rid: "90"}
	@PutMapping("/{bid}/{rid}")
	public ResponseEntity<List<BoardReply>> updateRep(@PathVariable Long bid,
			  String customer, @PathVariable Long rid, String reply) {
		System.out.println("reply:" + reply);
		BoardReply breply = new BoardReply();
		Board board = boardService.selectById(bid);
		breply.setBoard(board);
		breply.setCustomer(userService.getMemberInfoById(Integer.parseInt(customer)));
		breply.setRid(rid);
		breply.setReply(reply);
		System.out.println(breply);
		service.updateOrInsert(breply);
		
		return new ResponseEntity<>(service.selectAll(board), HttpStatus.OK);
	}


}
