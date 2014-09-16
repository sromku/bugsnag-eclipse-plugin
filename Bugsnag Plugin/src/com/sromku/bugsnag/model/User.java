package com.sromku.bugsnag.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@SerializedName("id")
	public String id;

	@Expose
	@SerializedName("email")
	public String email;

	@Expose
	@SerializedName("name")
	public String name;

	@Expose
	@SerializedName("gravatar_url")
	public String imageUrl;

	@Expose
	@SerializedName("account_admin")
	public Boolean isAdmin;
}
