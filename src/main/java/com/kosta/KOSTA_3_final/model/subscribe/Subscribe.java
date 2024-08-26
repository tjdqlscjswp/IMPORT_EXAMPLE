package com.kosta.KOSTA_3_final.model.subscribe;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tp_subscribe")


//구독
public class Subscribe {
   @Id
   @Column(name="subscribe_id")
   long subscribeId;
   
   
   @ManyToOne
   @JoinColumn(name = "customer_id")
   Member customer;
   
   @ManyToOne
   @JoinColumn(name = "package_id")
   public PackageVO pack;
   

   @CreationTimestamp
   @Column(name="payment_date")
   Date paymentDate;
   
   @Column(name="sub_check",columnDefinition = "integer default 1")
   int subCheck;
   

}
