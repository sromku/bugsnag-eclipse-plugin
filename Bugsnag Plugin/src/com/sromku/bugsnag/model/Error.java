package com.sromku.bugsnag.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @see https://bugsnag.com/docs/api/errors
 */
public class Error {
	
	@Expose
	@SerializedName("id")
	public String id;
	
	@Expose
	@SerializedName("last_message")
	public String message;
	
	@Expose
	@SerializedName("class")
	public String exception;
	
	@Expose
	@SerializedName("occurrences")
	public Integer occurrences;
	
	@Expose
	@SerializedName("release_stages")
	public Map<String, Integer> releaseStages;
	
	@Expose
	@SerializedName("last_context")
	public String where;
	
	@Expose
	@SerializedName("resolved")
	public Boolean isResolved;
	
	@Expose
	@SerializedName("first_received")
	public Date firstReceived;
	
	@Expose
	@SerializedName("last_received")
	public Date lastReceived;
	
	@Expose
	@SerializedName("severity")
	public String severity;
	
	@Expose
	@SerializedName("users_affected")
	public Integer affectedUsers;
	
	@Expose
	@SerializedName("comments")
	public Integer comments;
	
	@Expose
	@SerializedName("app_versions")
	public Map<String, Integer> appVersions;
	
	public List<Event> events;
}
