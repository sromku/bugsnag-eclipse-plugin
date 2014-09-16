package com.sromku.bugsnag.model;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

	@Expose
	@SerializedName("id")
	public String id;
	
	@Expose
	@SerializedName("message")
	public String message;
	
	@Expose
	@SerializedName("created_at")
	public Date created;
	
	@Expose
	@SerializedName("user")
	public User user;
}
