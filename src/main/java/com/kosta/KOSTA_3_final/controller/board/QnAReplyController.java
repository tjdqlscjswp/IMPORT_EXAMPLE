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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.KOSTA_3_final.model.board.Board;
import com.kosta.KOSTA_3_final.model.board.BoardReply;
import com.kosta.KOSTA_3_final.model.board.QnA;
import com.kosta.KOSTA_3_final.model.board.QnAReply;
import com.kosta.KOSTA_3_final.service.board.QnAReplyService;
import com.kosta.KOSTA_3_final.service.user.UserService;

@RestController
@RequestMapping("/qreplies/*")
public class QnAReplyController {
	
	@Autowired
	QnAReplyService service;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/QnA/{qid}")
	public ResponseEntity<List<QnAReply>> selectAll(@PathVariable Long qid) {
		QnA qna = QnA.builder().qid(qid).build();
		System.out.println("???:"+ service.selectAll(qna));
		return new ResponseEntity<>(service.selectAll(qna), HttpStatus.OK);
	}
	
	//댓글상세보기
	@GetMapping("{qrid}")
	public ResponseEntity<QnAReply> viewReply(@PathVariable Long qrid) {	
		return new ResponseEntity<>(service.selectById(qrid), HttpStatus.OK);
	}
	
	
	
	//특정 보드에 댓글등록, 입려 후 다시 조회
	@PostMapping("/{qid}")
	public ResponseEntity<List<QnAReply>> addapply(@PathVariable("qid")Long qid, QnAReply qreply, String email) {
		System.out.println("e:"+email);
		
		QnA qna = QnA.builder().qid(qid).build();
		qreply.setQna(qna);
		qreply.setAdmin(userService.getMemberInfo(email));
		service.updateOrInsert(qreply);
		return new ResponseEntity<>(service.selectAll(qna), HttpStatus.CREATED);
	}
	
		
	
	
	//삭제
	@DeleteMapping("/{qid}/{qrid}")
	public ResponseEntity<List<QnAReply>> deleteByqrid(@PathVariable Long qrid, @PathVariable Long qid) {
		service.delete(qrid);
		QnA qnA = QnA.builder().qid(qid).build();
		
		return new ResponseEntity<>(service.selectAll(qnA), HttpStatus.OK);
	}
	
}
