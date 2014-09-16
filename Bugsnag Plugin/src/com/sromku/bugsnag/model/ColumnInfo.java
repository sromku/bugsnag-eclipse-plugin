package com.sromku.bugsnag.model;

import java.io.Serializable;

public class ColumnInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String name;
	public int width;
	public boolean selected;
	
	public static ColumnInfo create(String name, int width, boolean selected) {
		ColumnInfo columnConfig = new ColumnInfo();
		columnConfig.name = name;
		columnConfig.width = width;
		columnConfig.selected = selected;
		return columnConfig;
	}
	
	public Column getColumn() {
		return Column.fromName(name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof ColumnInfo)) {
			return false;
		}
		ColumnInfo other = (ColumnInfo) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	
}
