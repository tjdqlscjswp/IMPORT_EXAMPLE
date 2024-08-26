package com.kosta.KOSTA_3_final.persistance.product_package;

import java.util.List;

import com.kosta.KOSTA_3_final.model.product_package.Product;

public interface CustomProduct {
	public List<Product> getProductList(String type, String keyword);
}
