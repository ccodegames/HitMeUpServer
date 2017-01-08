package com.ccode.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * HMUUser is a model representing a User of HMU. The user object encapsulates data related to the user such as username, password, etc.
 * implements Serializable to be writable to file.
 * 
 * @author mcjcloud
 *
 */
public class HMUUser implements Serializable {
	
	/**
	 * the Serialization UID for the object.
	 */
	private static final long serialVersionUID = 1941932358752315697L;
	
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private BufferedImage profileImage;
	
	private ArrayList<HMUUser> friends;
	private ArrayList<HMUUser> blocked;
	
	private String email;
	private String dateCreated;
	private String mood;								// TODO: maybe change data type
	
	/**
	 * init a user with all data except profileImage, and feeling.
	 * 
	 * @return HMUUser
	 */
	public HMUUser(String username, String password, String firstName, String lastName, String email) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		
		// set date created to current date
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		this.dateCreated = format.format(new Date());
		
		// init arrays (empty)
		friends = new ArrayList<HMUUser>();
		blocked = new ArrayList<HMUUser>();
	}
	
	
	/*
	 *  getter/setter methods
	 */
	// Username
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	// Password
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	// First Name
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	// Last Name
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	// Profile Image
	public BufferedImage getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(BufferedImage profileImage) {
		this.profileImage = profileImage;
	}
	
	// Friends
	public ArrayList<HMUUser> getFriends() {
		return friends;
	}
	public void addFriend(HMUUser friend) {
		friends.add(friend);
	}
	public void removeFriend(String username) {
		// remove a friend based on username.
		friends.removeIf(e -> e.username.equals(username));
	}
	
	// Blocked
	public ArrayList<HMUUser> getBlocked() {
		return blocked;
	}
	public void block(HMUUser user) {
		blocked.add(user);
	}
	public void unblock(HMUUser user) {
		blocked.removeIf(e -> e.username.equals(user.username));
	}
	
	// Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	// Date Created
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	// Mood
	public String getMood() {
		return mood;
	}
	public void setMood(String mood) {
		this.mood = mood;
	}
	
}
