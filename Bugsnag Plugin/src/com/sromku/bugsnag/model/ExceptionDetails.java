package com.sromku.bugsnag.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExceptionDetails {

	@Expose
	@SerializedName("class")
	public String className;

	@Expose
	@SerializedName("message")
	public String message;

	@Expose
	@SerializedName("stacktrace")
	public StacktraceLine[] stacktrace;

	public static class StacktraceLine {

		@Expose
		@SerializedName("file")
		public String file;

		@Expose
		@SerializedName("line")
		public Integer line;

		@Expose
		@SerializedName("method")
		public String method;

	}
}
