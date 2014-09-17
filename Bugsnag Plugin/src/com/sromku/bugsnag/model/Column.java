package com.sromku.bugsnag.model;

public enum Column {

	EXCEPTION("Exception"),
	CLASS("Class"),
	LOCATION("Location"),
	MESSAGE("Message"),
	OCCURRENCES("Occurrences"),
	AFFECTED_USERS("Affected users"),
	APP_VERSION("App version"),
	IS_RESOLVED("Is resolved"),
	RELEASE_STAGES("Stages"),
	SEVERITY("Severity"),
	COMMENTS("Comments"),
	CREATED_DATE("First received"),
	LAST_DATE("Last received");

	private String name;

	private Column(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Column fromName(String name) {
		for (Column column : values()) {
			if (column.name.equals(name)) {
				return column;
			}
		}
		return null;
	}

}
