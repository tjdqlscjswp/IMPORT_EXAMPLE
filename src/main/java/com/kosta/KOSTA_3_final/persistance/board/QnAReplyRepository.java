package com.kosta.KOSTA_3_final.persistance.board;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kosta.KOSTA_3_final.model.board.QnA;
import com.kosta.KOSTA_3_final.model.board.QnAReply;


public interface QnAReplyRepository extends CrudRepository<QnAReply, Long>{
	public List<QnAReply> findByQna(QnA qna);
}
