package com.kosta.KOSTA_3_final.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.KOSTA_3_final.model.board.QnA;
import com.kosta.KOSTA_3_final.model.board.QnAReply;
import com.kosta.KOSTA_3_final.persistance.board.QnAReplyRepository;

@Service
public class QnAReplyService {

	
	@Autowired
	QnAReplyRepository repository;
	
	
	public List<QnAReply> selectAll(QnA qna) {
		 
		return (List<QnAReply>) repository.findByQna(qna);
	}
	
	public QnAReply selectById(Long qrid) {
		return repository.findById(qrid).get();
	}
	
	public QnAReply updateOrInsert(QnAReply reply) {
		return repository.save(reply);
	}
	
	public int delete(Long qrid) {
		int ret=0;
		try {
			repository.deleteById(qrid);
		 ret=1;
		}catch(Exception e) {
			
		}
		return ret;
	}
	
	
	

}
