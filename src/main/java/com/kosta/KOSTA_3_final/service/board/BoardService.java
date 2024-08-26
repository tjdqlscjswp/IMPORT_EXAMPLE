package com.kosta.KOSTA_3_final.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kosta.KOSTA_3_final.model.board.Board;
import com.kosta.KOSTA_3_final.model.board.PageVO;
import com.kosta.KOSTA_3_final.persistance.board.BoardPersistance;
import com.querydsl.core.types.Predicate;

@Service
public class BoardService {

	@Autowired
	BoardPersistance persistance;
	
	public Page<Board> selectAll(PageVO pvo) {
		Predicate p = persistance.makePredicate(pvo.getType(),pvo.getKeyword());
		
		Pageable pageable = pvo.makePaging(0, "bid");
		System.out.println(p);
		System.out.println(pageable);
		Page<Board> result = persistance.findAll(p, pageable);
		
		return result;
	}
	
	public Board selectById(Long bid) {
		return persistance.findById(bid).get();
	}
	
	public Board insertBoard(Board board) {
		return persistance.save(board);
	}
	
	public Board updateBoard(Board board) {
		return persistance.save(board);
	}
	
	public int deleteBoard(Long bid) {
		int ret=0;
		try {
			persistance.deleteById(bid);
		ret=1;
		}catch(Exception ex){
		}
		return ret;
	}

}
