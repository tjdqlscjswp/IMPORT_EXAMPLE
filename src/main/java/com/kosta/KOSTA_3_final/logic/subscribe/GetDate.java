package com.kosta.KOSTA_3_final.logic.subscribe;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class GetDate {
	public Date getDate() {
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd");
		Calendar time = Calendar.getInstance();
		String format_time1 = format1.format(time.getTime());

		Date date = Date.valueOf(format_time1);
		return date;
	}
}
