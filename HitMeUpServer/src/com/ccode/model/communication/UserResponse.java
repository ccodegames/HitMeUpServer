package com.ccode.model.communication;

import java.io.Serializable;
import java.util.ArrayList;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

public class UserResponse implements Serializable {
	private String type = "userResponse";
	private String username;
	private String firstName;
	private String lastName;
	private String profileImage;
	private String email;
	private String dateCreated;
	private ArrayList<String> friends;
	private ArrayList<String> blocked;
	private String mood;
	
	public UserResponse(String username, String firstName, String lastName, String profileImage, String email, String dateCreated, ArrayList<String> friends, ArrayList<String> blocked, String mood) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profileImage = profileImage;
		this.email = email;
		this.dateCreated = dateCreated;
		this.friends = friends;
		this.blocked = blocked;
		this.mood = mood;
	}
	
	public String getJson() {
		JsonObject obj = new JsonObject();
		obj.add("type", type);
		obj.add("username", username);
		obj.add("firstName", firstName);
		obj.add("lastName", lastName);
		obj.add("profileImage", profileImage);
		obj.add("email", email);
		obj.add("dateCreated", dateCreated);
		
		// create friends
		JsonArray friendsArr = new JsonArray();
		for(String username : friends) {
			friendsArr.add(username);
		}
		// create blocked
		JsonArray blockedArr = new JsonArray();
		for(String username : blocked) {
			blockedArr.add(username);
		}
		
		obj.add("friends", friendsArr);
		obj.add("blocked", blockedArr);
		obj.add("mood", mood);
		
		// return the assembled json
		return obj.toString();
	}
}
