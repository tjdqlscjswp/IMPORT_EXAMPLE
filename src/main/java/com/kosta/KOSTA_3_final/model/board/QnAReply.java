package com.kosta.KOSTA_3_final.model.board;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.kosta.KOSTA_3_final.model.user.Member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "admin")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(of = "qrid")
@Table(name = "tp_question_board_reply")
//문의게시판_댓글VO
public class QnAReply{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="question_reply_id")
	Long qrid;
	
	@Column(name="question_reply")
	String qreply;
	
	@ManyToOne(fetch = FetchType.EAGER)
	QnA qna;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	Member admin;
	
	
	@CreationTimestamp
	@Column(name="question_reply_reg_date")
	Timestamp qreplyregDate;

	@UpdateTimestamp
	@Column(name="question_reply_update_date")
	Timestamp qrupdateDate;
}
