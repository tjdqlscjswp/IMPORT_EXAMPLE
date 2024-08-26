package com.kosta.KOSTA_3_final.service.product_package;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class PackageId {
	public String getPackageId() {
		
		SimpleDateFormat format1 = new SimpleDateFormat ( "yy-MM-dd HH:mm:ss");
		
		Date time = new Date();
		
		String time1 = format1.format(time);
		int randomNum = (int)(Math.random() * 10000 +1); 
		String intStr1 = time1.replaceAll("[^0-9]", "");
		
		return intStr1+randomNum;
		

	}
}
