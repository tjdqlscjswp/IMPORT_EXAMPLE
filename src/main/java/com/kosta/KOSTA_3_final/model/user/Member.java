package com.kosta.KOSTA_3_final.model.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kosta.KOSTA_3_final.model.board.Board;
import com.kosta.KOSTA_3_final.model.board.BoardReply;
import com.kosta.KOSTA_3_final.model.board.QnA;
import com.kosta.KOSTA_3_final.model.board.QnAReply;
import com.kosta.KOSTA_3_final.model.board.Review;
import com.kosta.KOSTA_3_final.model.product_package.Product;
import com.kosta.KOSTA_3_final.model.subscribe.PaymentList;
import com.kosta.KOSTA_3_final.model.subscribe.Subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//user
@Getter
@Setter
@Entity

@ToString(exclude = {"boards","payments","qnas","qnareplies","subscribes","reviews","boardreplies"})

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tp_user")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long customer_id;
	@Column(name = "customer_name")
	String customerName;
	@Column(unique = true)
	String email;
	String password;
	String phone_number;
	String address;
	String postnumber;
	String addressdetail;
	@Enumerated(EnumType.STRING)
	UserRoleEnumType auth;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL) //, fetch = FetchType.EAGER
	List<Board> boards;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL) //, fetch = FetchType.EAGER
	List<BoardReply> boardreplies;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL) //, fetch = FetchType.EAGER
	List<PaymentList> payments;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL) //, fetch = FetchType.EAGER
	List<QnA> qnas;
	
	@JsonIgnore
	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL) //, fetch = FetchType.EAGER
	List<QnAReply> qnareplies; 
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Subscribe> subscribes;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL) //, fetch = FetchType.EAGER
	List<Review> reviews;
	
	
	
	
	
	
	
	
	
}
