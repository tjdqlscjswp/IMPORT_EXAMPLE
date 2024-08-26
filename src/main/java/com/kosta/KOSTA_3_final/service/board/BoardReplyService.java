package com.kosta.KOSTA_3_final.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.kosta.KOSTA_3_final.model.board.Board;
import com.kosta.KOSTA_3_final.model.board.BoardReply;
import com.kosta.KOSTA_3_final.persistance.board.BoardReplyPersistance;


@Service
public class BoardReplyService {
	
	@Autowired
	BoardReplyPersistance persistance;
	
	
	public List<BoardReply> selectAll(Board board) {
		return (List<BoardReply>) persistance.findByBoard(board, Sort.by(Direction.ASC, "rid"));
	}
	
	public BoardReply selectById(Long rid) {
		return persistance.findById(rid).get();
	}
	
	public BoardReply updateOrInsert(BoardReply reply) {
		return persistance.save(reply);
	}
	
	public int delete(Long rid) {
		int ret=0;
		try {
		 persistance.deleteById(rid);
		 ret=1;
		}catch(Exception e) {
			
		}
		return ret;
	}
	

}
