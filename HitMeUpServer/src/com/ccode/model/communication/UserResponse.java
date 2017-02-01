package com.ccode.model.communication;

import java.io.Serializable;
import java.util.ArrayList;

import com.ccode.model.HMUUser;
import com.ccode.model.UserRelationProperty;
import com.ccode.model.UserRelationProperty;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

public class UserResponse implements Serializable {
	private String type = "user";
	private String guid;
	private boolean authenticated;
	private String username;
	private String firstName;
	private String lastName;
	private String profileImage;
	private String email;
	private String dateCreated;
	private ArrayList<String> relationProperties;
	private ArrayList<String> friends;
	private ArrayList<String> blocked;
	private String mood;
	
	public UserResponse(boolean authenticated, String guid, String username, String firstName, String lastName, String profileImage, String email, String dateCreated, ArrayList<String> friends, ArrayList<String> blocked, ArrayList<String> relationProperties, String mood) {
		
		this.authenticated = authenticated;
		this.guid = guid;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profileImage = profileImage;
		this.email = email;
		this.dateCreated = dateCreated;
		this.friends = friends;
		this.blocked = blocked;
		this.relationProperties = relationProperties;
		this.mood = mood;
		
		if(this.friends == null) {
			this.friends = new ArrayList<String>();
		}
		if(this.blocked == null) {
			this.blocked = new ArrayList<String>();
		}
		if(this.relationProperties == null) {
			this.relationProperties = new ArrayList<String>();
			this.relationProperties.add(UserRelationProperty.None.toString());
		}
	}
	
	public UserResponse(HMUUser user, ArrayList<UserRelationProperty> relationProperties) {
		this.authenticated = false;
		this.guid = user.getGUID();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.profileImage = user.getBase64ProfileImage();
		this.email = user.getEmail();
		this.dateCreated = user.getDateCreated();
		
		friends = new ArrayList<String>();
		// get friend and blocked array
		for(HMUUser friend : user.getFriends()) {
			friends.add(friend.getUsername());
		}
		blocked = new ArrayList<String>();
		for(HMUUser block : user.getBlocked()) {
			blocked.add(block.getUsername());
		}
		
		this.mood = user.getMood().toString();
		
		// check user relations
		this.relationProperties = new ArrayList<String>();
		if(relationProperties == null) {
			this.relationProperties.add(UserRelationProperty.None.toString());
		}
		else {
			for(UserRelationProperty prop : relationProperties) {
				this.relationProperties.add(prop.toString());
			}
		}
		
	}
	
	public static UserResponse getUnauthenticatedResponse() {
		return new UserResponse(false, "", "", "", "", "", "", "", null, null, null, "");
	}
	
	public JsonObject getJson() {
		JsonObject obj = new JsonObject();
		obj.add("type", type);
		obj.add("authenticated", authenticated);
		obj.add("userId", guid);
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
		JsonArray propArray = new JsonArray();
		for(String prop : relationProperties) {
			propArray.add(prop);
		}
		
		obj.add("friends", friendsArr);
		obj.add("blocked", blockedArr);
		obj.add("relationProperties", propArray);
		obj.add("mood", mood);
		
		// return the assembled json
		return obj;
	}
}
