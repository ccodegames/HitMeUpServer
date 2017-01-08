package com.ccode.network;

import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.ccode.model.HMUUser;
import com.ccode.model.communication.UserResponse;
import com.ccode.model.data.DataManager;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * ClientConnection extends Socket but adds functionality to manage the user that the Socket is associate with.
 * Handles communication to and from the client.
 * 
 * @author mcjcloud
 *
 */
public class ClientConnection {
	public static final int INPUT_BUFFER_SIZE = 2048;
	
	private ClientManager manager;
	private Socket socket;
	
	private HMUUser user;
	private boolean keepListening = true;
	
	private InputStream inputStream;
	private OutputStream outputStream;
	
	/**
	 * default constructor
	 */
	public ClientConnection(ClientManager manager, Socket socket) {
		this.manager = manager;
		this.socket = socket;
		
		// init streams
		try {
			System.out.println("outputstream");
			// construct and flush output stream
			outputStream = this.socket.getOutputStream();
			outputStream.flush();
			
			System.out.println("inputstream");
			// construct inputStream
			inputStream = this.socket.getInputStream();
			System.out.println("post inputstream");
			
			// start listening
			listen();
		}
		catch(IOException ioe) {
			System.out.println("inputStream: " + inputStream);
			System.out.println("outputStream: " + outputStream);
			ioe.printStackTrace();
		}
	}
	/**
	 * Constructor that inits the user object of the connection.
	 * 
	 * @param user
	 */
	public ClientConnection(ClientManager manager, Socket socket, HMUUser user) {
		this.manager = manager;
		this.socket = socket;
		this.user = user;
		
		// init streams
		try {
			System.out.println("outputstream");
			// construct and flush output stream
			outputStream = this.socket.getOutputStream();
			outputStream.flush();
			
			System.out.println("inputstream");
			// construct inputStream
			inputStream = this.socket.getInputStream();
			System.out.println("post inputstream");
			
			// start listening
			listen();
		}
		catch(IOException ioe) {
			System.out.println("inputStream: " + inputStream);
			System.out.println("outputStream: " + outputStream);
			ioe.printStackTrace();
		}
	}
	
	/*
	 * Getter/setter
	 */
	public HMUUser getUser() {
		return user;
	}
	public void setUser(HMUUser user) {
		this.user = user;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	
	/**
	 * starts listening for messages on a separate thread.
	 */
	public void listen() {
		Thread listener = new Thread(() -> {
			try {
				// infinite loop to listen to messages on.
				String message = "";
				while(keepListening) {
					System.out.println("waiting for message...");
					byte[] input = new byte[INPUT_BUFFER_SIZE];	// TODO: make sure a buffer overflow can't be caused.
					int count = inputStream.read(input);
					System.out.println("count: " + count);
					message += new String(input).trim();
					
					if(count == -1) {
						// client disconnected.
						break;
					}
					else if(message.endsWith("[END]")) {
						// parse and handle the input.
						String finalMessage = message.substring(0, message.length() - "[END]".length());
						parseInput(finalMessage);
						message = "";
					}
				}
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			finally {
				// close connections
				System.out.println("client disconnected... cleaning up");
				stop();
			}
		});
		listener.start();
	}
	
	public void stop() {
		// stop the listening thread.
		this.keepListening = false;
		// close the streams
		try {
			inputStream.close();
			outputStream.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		manager.disconnect(this);
		System.out.println("clients after disconnect: " + manager.getClients().toString());
	}
	
	/**
	 * handles the message received by the Socket.
	 * 
	 * @param input
	 */
	public void parseInput(String input) {
		// TODO: convert to json object and parse.
		String jsonString = input.replaceAll("[END]", "");
		JsonObject inputJson = Json.parse(jsonString).asObject();
		
		String type = inputJson.get("type").asString();
		switch(type) {
		case "login":
			String username = inputJson.get("username").asString();
			String password = inputJson.get("password").asString();
			login(username != null ? username : "", password != null ? password : "");
			break;
			
		}
	}
	
	
	// return back to this client.
	public void returnData(String message) {
		message += "[END]";
		byte[] output = message.getBytes();
		try {
			outputStream.write(output);
			outputStream.flush();
		}
		catch(IOException ioe) {
			System.out.println("error writing back to client");
			ioe.printStackTrace();
		}
	}
	/*
	 * message methods
	 */
	public void login(String username, String password) {
		Thread loginThread = new Thread(() -> {
			// TODO: handle a login request json
			HMUUser requestedUser = DataManager.getUsersWhere(e -> e.getUsername().equals(username)).get(0);	// gets the first user that matches usernames.
			if(requestedUser != null) {
				if(requestedUser.getPassword().equals(password)) {
					// user authenticated
					this.user = requestedUser;
					
					// send back UserResponse.
					// get image data
					String encodeImage = "";
					if(user.getProfileImage() != null) {
						WritableRaster raster = user.getProfileImage().getRaster();
						DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
						encodeImage = Base64.encode(data.getData());
					}
					
					// get friends list and blocked list
					ArrayList<String> friendUsernames = new ArrayList<String>();
					ArrayList<String> blockedUsernames = new ArrayList<String>();
					for(HMUUser user : user.getFriends()) {
						friendUsernames.add(user.getUsername());
					}
					for(HMUUser user : user.getBlocked()) {
						blockedUsernames.add(user.getUsername());
					}
					UserResponse response = new UserResponse(user.getUsername(), user.getFirstName(), user.getLastName(), encodeImage, user.getEmail(), user.getDateCreated(), friendUsernames, blockedUsernames, user.getMood());
					String responseJson = response.getJson();
					
					// return the data back.
					returnData(responseJson);
				}
				else {
					// TODO: respond with fail.
				}
			}
		});
		loginThread.start();
	}
	
	public void sendText(String message) {
		Thread sender = new Thread(() -> {
			// TODO: handle sending message to recipients with Message json
		});
		sender.start();
	}
	
}
