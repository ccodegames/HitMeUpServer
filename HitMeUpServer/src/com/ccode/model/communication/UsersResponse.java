package com.ccode.model.communication;

import java.util.ArrayList;

import com.ccode.model.HMUUser;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

public class UsersResponse {
	JsonArray responses;

	public UsersResponse(ArrayList<HMUUser> users, HMUUser recipient) {
		responses = new JsonArray();
		for(HMUUser user : users) {
			responses.add(new UserResponse(user, recipient.relationsTo(user)).getJson());
		}
	}
	
	public JsonObject getJson() {
		JsonObject result = new JsonObject();
		result.add("type", "usersResponse");
		result.add("users", responses.asArray());
		
		return result;
	}
}
