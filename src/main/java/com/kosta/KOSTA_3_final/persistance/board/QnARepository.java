package com.kosta.KOSTA_3_final.persistance.board;


import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import com.kosta.KOSTA_3_final.model.board.QQnA;
import com.kosta.KOSTA_3_final.model.board.QnA;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface QnARepository extends CrudRepository<QnA, Long>, QuerydslPredicateExecutor<QnA>{

	
	  	//@Query(value = "select * from TP_QUESTION_BOARD where customer_id = ?", nativeQuery = true)
	  	//public Page<QnA> findByUser(Predicate p, Pageable pageable, Integer id);
	 
	    
		public default Predicate makePredicate(String type, String keyword) {
			BooleanBuilder builder = new BooleanBuilder();
			QQnA QnA = QQnA.qnA;

			if(type==null) return builder;
			builder.and(QnA.qid.gt(0)); 
			
			switch(type) {
			case "qtitle":
				builder.and(QnA.qtitle.like("%"+keyword+"%"));
				break;
			case "qcontent":
				builder.and(QnA.qcontent.like("%"+keyword+"%"));
				break;
			case "email":
				builder.and(QnA.customer.email.like("%"+keyword+"%"));
				break;
			default:
				break;
			}
			return builder;
		}
		public default Predicate makePredicate2(String type, String keyword, int cid) {
			BooleanBuilder builder = (BooleanBuilder)makePredicate(type, keyword);
			QQnA QnA = QQnA.qnA;
			builder.and(QnA.customer.customer_id.eq((long) cid)); 
			return builder;
		}
}
