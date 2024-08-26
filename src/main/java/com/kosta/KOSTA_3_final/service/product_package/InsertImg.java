package com.kosta.KOSTA_3_final.service.product_package;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;


import org.springframework.stereotype.Component;

import com.kosta.KOSTA_3_final.model.product_package.ProductListVO;

@Component
public class InsertImg {
	public void insertImg(ProductListVO productList) {
		if(productList.getProductName() == null) {
			String[] length = {"0"};
			productList.setProductName(length);
		}
		
		IntStream.range(0, productList.getProductName().length).forEach(idx->{			
		    
		    String rootPath = System.getProperty("user.dir");
		    String filePath = rootPath + "/src/main/resources/static/images/product_images/" + productList.getFiles()[idx].getOriginalFilename();
		    System.out.println(filePath);
		    File dest = new File(filePath);
		    System.out.println(dest);

		    try {
				productList.getFiles()[idx].transferTo(dest);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}