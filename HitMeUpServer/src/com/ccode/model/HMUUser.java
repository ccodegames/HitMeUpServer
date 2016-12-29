package com.ccode.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

/**
 * HMUUser is a model representing a User of HMU. The user object encapsulates data related to the user such as username, password, etc.
 * implements Serializable to be writtable to file.
 * 
 * @author mcjcloud
 *
 */
public class HMUUser implements Serializable {
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private ImageIcon profileImage;
	
	private ArrayList<HMUUser> friends;
	
	private String email;
	private String dateCreated;
	private String feeling;								// TODO: maybe change data type
	
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
	}
	
	
	/*
	 *  getter/setter methods
	 */
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public ImageIcon getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(ImageIcon profileImage) {
		this.profileImage = profileImage;
	}
	
	public ArrayList<HMUUser> getFriends() {
		return friends;
	}
	public void addFriend(HMUUser friend) {
		friends.add(friend);
	}
	public void removeFriend(String username) {
		// remove a friend based on username.
		friends.removeIf(e -> e.username == username);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getFeeling() {
		return feeling;
	}
	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}
	
}
