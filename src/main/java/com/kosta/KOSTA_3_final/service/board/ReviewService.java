package com.kosta.KOSTA_3_final.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kosta.KOSTA_3_final.model.board.PageVO;
import com.kosta.KOSTA_3_final.model.board.Review;
import com.kosta.KOSTA_3_final.persistance.board.ReviewRepository;
import com.querydsl.core.types.Predicate;
@Service
public class ReviewService {
	
	@Autowired
	ReviewRepository repository;
	

	public Page<Review> selectAll(PageVO pagevo) {
		Predicate p = repository.makePredicate(pagevo.getType(),pagevo.getKeyword());
		
		Pageable pageable = pagevo.makePaging(0, "reviewId");
		Page<Review> result = repository.findAll(p, pageable);
		
		return result;
	}

	public Review insertReview(Review review) {
		return repository.save(review);
	}
	
}
