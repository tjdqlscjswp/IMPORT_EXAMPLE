package com.kosta.KOSTA_3_final.model.board;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString(exclude = "qreplies")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(of = "qid")
@Table(name = "tp_question_board")
//문의게시판VO
public class QnA {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	Long qid;

	@Column(name = "question_title")
	String qtitle;
	@Column(name = "question_content")
	String qcontent;


	@ManyToOne
	@JoinColumn(name = "customer_id")
	Member customer; // 문의글 작성자

	@CreationTimestamp
	@Column(name = "question_reg_date")
	Timestamp qregDate;

	@UpdateTimestamp
	@Column(name = "question_updatedate")
	Timestamp qupdateDate;

	
	 @JsonIgnore // tostring과 유사, JSON만들때 무한loop 방지
	 @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL, fetch =FetchType.LAZY)
	 List<QnAReply> qreplies;
	 

}
