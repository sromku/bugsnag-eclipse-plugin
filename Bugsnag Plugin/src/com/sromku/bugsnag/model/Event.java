package com.sromku.bugsnag.model;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

	@Expose
	@SerializedName("id")
	public String id;
	
	@Expose
	@SerializedName("received_at")
	public Date received;
	
	@Expose
	@SerializedName("user_id")
	public String userId;
	
	@Expose
	@SerializedName("app_version")
	public String appVersion;

	@Expose
	@SerializedName("os_version")
	public String osVersion;
	
	@Expose
	@SerializedName("context")
	public String where;
	
	@Expose
	@SerializedName("severity")
	public String severity;
	
	@Expose
	@SerializedName("meta_data")
	public Metadata metadata;
	
	@Expose
	@SerializedName("exceptions")
	public ExceptionDetails[] exceptions;
	
}
