package com.kosta.KOSTA_3_final.model.product_package;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//상품_패키지 연결VO
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tp_productpack")
@SequenceGenerator(
		  name = "PP_ID_EX_SEQ", 
		  sequenceName = "PP_ID_SEQ", // 매핑할 데이터베이스 시퀀스 이름 
		  allocationSize = 1)
public class Product_Package {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "PP_ID_EX_SEQ")
	int ppId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id")
	PackageVO pack;
	
	int product_qty;
}
