package com.kosta.KOSTA_3_final.model.subscribe;
//결제내역

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import com.kosta.KOSTA_3_final.model.user.Member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tp_payment")
public class PaymentList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int payment_id;
	
	@ManyToOne
	@JoinColumn(name="subscribe_id")
	Subscribe subscribe;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	Member customer;
	
}
