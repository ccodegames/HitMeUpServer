package com.ccode.network;

public enum MessageType {
	
	TEXT, 
	IMAGE, 
	DISCONNECT, 
	LOGIN, 
	CREATE, 
	INSTRUCTION;
	
	@Override
	public String toString() {
		switch(this) {
		case TEXT:
			return "[TEXT]";
		case IMAGE:
			return "[IMAGE]";
		case DISCONNECT:
			return "[DISCONNECT]";
		case LOGIN:
			return "[LOGIN]";
		case CREATE:
			return "[CREATE]";
		case INSTRUCTION:
			return "[INSTRUCTION]";
		default:
			return "default";
		}
	}
}
