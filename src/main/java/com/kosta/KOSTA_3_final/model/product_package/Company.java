package com.kosta.KOSTA_3_final.model.product_package;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//회사
@Getter
@Setter
@Entity
@ToString(exclude = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tp_company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int company_id;
	String company_name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //, fetch = FetchType.EAGER
	List<Product> products;
}
