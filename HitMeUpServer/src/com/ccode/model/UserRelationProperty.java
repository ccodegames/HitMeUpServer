package com.ccode.model;

public enum UserRelationProperty {
	None("none"), Friend("friend"), Blocked("blocked"), Self("self"), Query("query");
	
	private String state = "";
	UserRelationProperty(String state) {
		this.state = state;
	}
	public String getState() {
		return this.state;
	}
	
	@Override
	public String toString() {
		switch(this) {
		case None:
			return "none";
		case Friend:
			return "friend";
		case Blocked:
			return "blocked";
		case Self:
			return "self";
		case Query:
			return "query";
		default:
			return "none";
		}
	}
}
