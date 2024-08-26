package com.kosta.KOSTA_3_final;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;

import com.kosta.KOSTA_3_final.model.board.Board;
import com.kosta.KOSTA_3_final.model.board.BoardReply;
import com.kosta.KOSTA_3_final.model.board.PageVO;
import com.kosta.KOSTA_3_final.model.user.Member;
import com.kosta.KOSTA_3_final.model.user.UserRoleEnumType;
import com.kosta.KOSTA_3_final.persistance.board.BoardPersistance;
import com.kosta.KOSTA_3_final.persistance.board.BoardReplyPersistance;
import com.kosta.KOSTA_3_final.persistance.user.MemberRepository;
import com.kosta.KOSTA_3_final.service.user.UserService;
import com.querydsl.core.types.Predicate;

import lombok.extern.java.Log;
@Log
@Commit
@SpringBootTest
class Kosta3FinalApplicationTests {
	
	@Autowired
	BoardPersistance repo;
	@Autowired
	BoardReplyPersistance replyPersistance;
	@Autowired
	MemberRepository userPersistance;
	

	//@Test
	public void test1() {
		IntStream.range(100, 200).forEach(i->{
			Member cu = new Member();
		
			cu.setCustomer_id(25);
			cu.setAuth(UserRoleEnumType.USER);
			cu.setEmail("swk9514@naver.com");
		
			Board board = Board.builder()
					.btitle("FreeBoard title" +i)
					.bcontent("FreeBoard content" +i)
					.customer(cu)
					.build();
			
			repo.save(board);
		});
	}
	
	//@Test
	public void insertreply() {
		Long[] arr = {47L, 48L, 49L};
		
		
		
		Arrays.stream(arr).forEach(num -> {
			Board board = new Board();
			board.setBid(num);
			IntStream.range(400, 500).forEach(i -> {
				BoardReply reply = new BoardReply();
				reply.setReply("테스트용 댓글"+i);
				reply.setCustomer(userPersistance.findByCustomerName("swk9514@naver.com"));
				reply.setBoard(board);

				replyPersistance.save(reply);
			});
		});

	}
	
	
	
	
	//@Test
		public void conditionRetrieve() {
			Predicate p = repo.makePredicate(null, null);
			//Pageable pageable = PageRequest.of(0, 3);
			PageVO pvo = new PageVO();
			Pageable pageable = pvo.makePaging(0, "bid");
			Page<Board> result = repo.findAll(p, pageable);
			List<Board> boardlist = result.getContent();
			System.out.println("여긴오");
			boardlist.forEach(b->{
				System.out.println(b);
			});
			System.out.println("한페이지의 사이즈"+result.getSize());
			System.out.println("전체페이지"+result.getTotalPages());
		}
	
	
	
	@Transactional
	//@Test
	public void boardReplyCount() {
		repo.findById(690L).ifPresent(b->{
			System.out.println(b.getReplies().size());
		});
	}
	
	@Transactional
	//@Test
	public void insertReply() {
		repo.findById(47L).ifPresent(b->{
			System.out.println("찾음?");
			List<BoardReply> replies = b.getReplies();
			b.setBtitle("title 수정합니다1.");
			System.out.println("됨?");
			IntStream.range(1, 4).forEach(i->{
				BoardReply reply = BoardReply.builder()
						.reply("댓글..."+i)
						.board(b)
						.build();
				System.out.println("insert");
				replies.add(reply);
			});
			repo.save(b);
		});
	}
	
	
	

}
