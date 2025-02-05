package com.crm.listener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class main {

	public static void main(String[] args) {
		LocalDateTime date = LocalDateTime.now();
		String format = date.format(DateTimeFormatter.ofPattern("DD_MM_YY_hh_mm_ss"));
		System.out.println(format);
	}
}
