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

//대분류
@Getter
@Setter
@Entity
@ToString(exclude = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tp_largecl")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int categoryId;
	String categoryName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Product> products;
	
}
