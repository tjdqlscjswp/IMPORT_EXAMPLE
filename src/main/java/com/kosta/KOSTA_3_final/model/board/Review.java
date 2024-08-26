package com.kosta.KOSTA_3_final.model.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.kosta.KOSTA_3_final.model.product_package.PackageVO;

import com.kosta.KOSTA_3_final.model.user.Member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "pack")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "onelineboard")
//한줄평 별점
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "review_id")
	Long reviewId;
	
	@ManyToOne
	@JoinColumn(name = "package_id")
	PackageVO pack;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	Member customer; //변수명
	
	int score; //별점 모양으로 출력
	String review;
}
