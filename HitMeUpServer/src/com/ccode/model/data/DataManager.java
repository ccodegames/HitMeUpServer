package com.ccode.model.data;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.function.Predicate;

import com.ccode.model.HMUUser;

/**
 * DataManager is a class of static methods to read/write data to and from the disk
 * Manages HMUUsers
 * 
 * @author brayd
 *
 */
public class DataManager {
	
	// Constants
	public static final String DATA_DIRECTORY = System.getProperty("user.home") + "/hmuserver";
	public static final String USER_DIRECTORY = System.getProperty("user.home") + "/hmuserver/users.dat";
	
	// Data
	private static ArrayList<HMUUser> users;
	
	/**
	 * Loads the saved users from file into memory.
	 * 
	 * @return true if the users were loaded. False if there was an error.
	 * @throws IOException if the file couldn't be loaded.
	 */
	public static boolean loadUsers() throws IOException {
		// make sure the directory exists
		//File dataPath = new File(DATA_DIRECTORY);
		File userFile = new File(USER_DIRECTORY);
		//if(!dataPath.isDirectory()) dataPath.mkdir();
		if(!userFile.isFile()){
			if(userFile.getParentFile().mkdirs()) {
				if(userFile.createNewFile()) {
					// if the file was just created. The users aren't going to be there, so return false.
					users = new ArrayList<HMUUser>();
					return false;
				}
				else {
					throw new IOException();
				}
			}
			else {
				throw new IOException();
			}
		}
		else {
			// read the ArrayList from file.
			System.out.println("created streams");
			try {
				ObjectInputStream oin = new ObjectInputStream(new FileInputStream(userFile));
				users = (ArrayList<HMUUser>) oin.readObject();
				oin.close();
				System.out.println("users read: " + users);
			} catch(ClassNotFoundException cnfe) {
				// TODO: Handle this exception properly.
				cnfe.printStackTrace();
			}
			catch(EOFException eofe) {
				eofe.printStackTrace();
			}
			
			// return false if users is null, else true.
			if(users == null) {
				users = new ArrayList<HMUUser>();
				return false;
			}
			else {
				return true;
			}
		}
	}
	
	public static void saveUsers() throws IOException {
		// make sure the directory exists
		File dataPath = new File(DATA_DIRECTORY);
		File userFile = new File(USER_DIRECTORY);
		if(!dataPath.isDirectory()) dataPath.mkdirs();
		if(!userFile.isFile()) userFile.createNewFile();
		
		// write the ArrayList to file
		FileOutputStream fout = new FileOutputStream(userFile);
		ObjectOutputStream oout = new ObjectOutputStream(fout);
		oout.writeObject(users);
		oout.close();
	}
	
	// get/add/remove users
	public static ArrayList<HMUUser> getUsers() {
		return users;
	}
	public static void addUser(HMUUser user) {
		System.out.println("in adduser: " + users);
		users.add(user);
		System.out.println("added user to array in DataManager");
		// Save the users.
		try {
			saveUsers();
			System.out.println("saved users");
		} catch (IOException e) {
			System.out.println("Error saving users.");
			e.printStackTrace();
		}
		System.out.println("returning from addUser");
	}
	public static void removeUser(HMUUser user) {
		users.removeIf(e -> e.getUsername().equals(user.getUsername()));
		
		// Save the users.
		try {
			saveUsers();
		} catch (IOException e) {
			System.out.println("Error saving users.");
			e.printStackTrace();
		}
	}
	public static void removeAllUsers() {
		users.clear();
		
		// Save the users.
		try {
			saveUsers();
		} catch (IOException e) {
			System.out.println("Error saving users.");
			e.printStackTrace();
		}
	}
	public static ArrayList<HMUUser> getUsersWhere(Predicate<? super HMUUser> predicate) {
		ArrayList<HMUUser> result = new ArrayList<HMUUser>();
		for(HMUUser user : users) {
			if(predicate.test(user))
			{
				result.add(user);
			}
		}
		return result;
	}
}
