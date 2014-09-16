package com.sromku.bugsnag.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @see https://bugsnag.com/docs/api/projects
 */
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	public Account account;
	
	@Expose
	@SerializedName("id")
	public String id;

	@Expose
	@SerializedName("name")
	public String name;

	@Expose
	@SerializedName("created_at")
	public Date created;

	@Expose
	@SerializedName("updated_at")
	public Date updated;

	@Expose
	@SerializedName("release_stages")
	public String[] releaseStages;

	@Expose
	@SerializedName("api_key")
	public String apiKey;

	@Expose
	@SerializedName("errors")
	public int errors;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Project)) {
			return false;
		}
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
