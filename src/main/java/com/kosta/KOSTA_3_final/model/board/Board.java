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

import com.kosta.KOSTA_3_final.model.subscribe.Subscribe;

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
@ToString(exclude = "replies")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(of = "bid")
@Table(name = "tp_bulletin_board")
public class Board {
	
	@Id // 필수PK
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="board_id")
	Long bid;
	@Column(name="board_title")
	String btitle;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	Member customer; //댓글작성자

	
	@Column(name="board_content")
	String bcontent;
	
	@CreationTimestamp
	@Column(name="board_regdate")
	Timestamp bregdate;
	@UpdateTimestamp
	@Column(name="board_updatedate")
	Timestamp bupdatedate;

	@JsonIgnore // tostring과 유사, JSON만들때 무한loop 방지
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<BoardReply> replies;

}
