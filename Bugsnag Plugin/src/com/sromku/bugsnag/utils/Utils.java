package com.sromku.bugsnag.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

	public static Date toDate(String time) {
		if (time == null) {
			return null;
		}

		try {
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
			df.setTimeZone(tz);
			return df.parse(time);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toDate(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yy");
		return df.format(date);
	}

	public static String getWrappedString(String[] array) {
		String res = "";
		for (String str : array) {
			res = res + str + "\n";
		}
		return res;
	}
	
}
