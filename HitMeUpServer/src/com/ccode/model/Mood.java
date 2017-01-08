package com.ccode.model;

public enum Mood {
	Happy("Happy"), Sad("Sad");
	
	private String state = "";
	Mood(String state) {
		this.state = state;
	}
	public String getState() {
		return this.state;
	}
	
	@Override
	public String toString() {
		switch(this) {
		case Happy:
			return "Happy";
		case Sad:
			return "Sad";
		default:
			return "Happy";
		}
	}
}
