package com.sromku.bugsnag.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metadata {

	@Expose
	@SerializedName("App")
	public AppInfo appInfo;
	
	@Expose
	@SerializedName("Device")
	public DeviceInfo deviceInfo;
	
	@Expose
	@SerializedName("User")
	public User user;
}
