package com.sromku.bugsnag.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @see https://bugsnag.com/docs/api/accounts
 */
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Expose
	@SerializedName("id")
	public String id;
	
	public String authToken;

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
	@SerializedName("account_creator")
	public User creator;

	public List<Project> projects;

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
		if (!(obj instanceof Account)) {
			return false;
		}
		Account other = (Account) obj;
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
