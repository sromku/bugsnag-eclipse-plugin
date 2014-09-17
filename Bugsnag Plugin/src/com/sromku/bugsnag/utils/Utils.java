package com.sromku.bugsnag.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.sromku.bugsnag.model.ExceptionDetails;
import com.sromku.bugsnag.model.ExceptionDetails.StacktraceLine;

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

	public static String getWrappedString(ExceptionDetails exception) {
		String res = "";
		if (exception.stacktrace != null && exception.stacktrace.length > 0)
		for (StacktraceLine stacktraceLine : exception.stacktrace) {
			res += String.format(Locale.US, "%s(%s:%d)", stacktraceLine.method, stacktraceLine.file, stacktraceLine.line) + "\n";
		}
		return res;
	}
	
}
