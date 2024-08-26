package com.kosta.KOSTA_3_final.persistance.board;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.KOSTA_3_final.model.board.Board;
import com.kosta.KOSTA_3_final.model.board.BoardReply;


public interface BoardReplyPersistance extends JpaRepository<BoardReply, Long>{
	/*
	 * @Query(value =
	 * "select r.board_reply_id, b.board_id, b.BOARD_CONTENT, b.BOARD_REGDATE, b.BOARD_TITLE, b.BOARD_UPDATEDATE, b.CUSTOMER_ID"
	 * + " from TP_BULLETIN_BOARD b left outer join TP_BULLETIN_BOARD_REPLY r" +
	 * " on b.board_id = r.board_id order by r.board_reply_id desc", nativeQuery =
	 * true)
	 */
	
	public List<BoardReply> findByBoard(Board board, Sort sort);
}
