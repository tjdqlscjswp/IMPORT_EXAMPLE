package com.kosta.KOSTA_3_final.model.subscribe;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@ToString(exclude = {"pack","customer"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="tp_delivery")



//배송
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "delivery_id")
	long deliveryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id")
	PackageVO pack;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	Member customer;
	
	@Column(name = "delivery_date")
	Date deliveryDate;	
}
