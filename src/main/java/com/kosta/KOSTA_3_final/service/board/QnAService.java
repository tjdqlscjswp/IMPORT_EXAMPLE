package com.kosta.KOSTA_3_final.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kosta.KOSTA_3_final.model.board.PageVO;
import com.kosta.KOSTA_3_final.model.board.QnA;
import com.kosta.KOSTA_3_final.persistance.board.QnARepository;
import com.querydsl.core.types.Predicate;

@Service
public class QnAService {
	@Autowired
	QnARepository repository;
	
	public Page<QnA> selectAll(PageVO pvo) {
		Predicate p = repository.makePredicate(pvo.getType(),pvo.getKeyword());
		
		Pageable pageable = pvo.makePaging(0, "qid");
		Page<QnA> result = repository.findAll(p, pageable);
		return result;
	}
	
	public Page<QnA> selectByUser(PageVO pvo, Integer id) {
		
		Predicate p = repository.makePredicate2(pvo.getType(),pvo.getKeyword(), id);
		
		Pageable pageable = pvo.makePaging(0, "qid");
		Page<QnA> result = repository.findAll(p, pageable);
		return result;
		
		
//		Predicate p = repository.makePredicate(pvo.getType(),pvo.getKeyword());
//		
//		Pageable pageable = pvo.makePaging(0, "qid");
//		Page<QnA> result = repository.findByUser(p, pageable, id);
//		return result;
	}
	
	public QnA selectById(Long qid) {
		return repository.findById(qid).get();
	}
	
	public QnA insertQnA(QnA qnA) {
		return repository.save(qnA);
	}
	
	public QnA updateQnA(QnA qnA) {
		return repository.save(qnA);
	}
	
	public int deleteQnA(Long qid) {
		int ret=0;
		try {
			repository.deleteById(qid);
		ret=1;
		}catch(Exception ex){
		}
		return ret;
	}

}
