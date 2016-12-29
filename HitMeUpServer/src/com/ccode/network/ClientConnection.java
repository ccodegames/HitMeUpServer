package com.ccode.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.ccode.model.HMUUser;

/**
 * ClientConnection extends Socket but adds functionality to manage the user that the Socket is associate with.
 * Handles communication to and from the client.
 * 
 * @author mcjcloud
 *
 */
public class ClientConnection extends Socket {
	private ClientManager manager;
	private HMUUser user;
	private boolean keepListening = true;
	
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	/**
	 * default constructor
	 */
	public ClientConnection(ClientManager manager) {
		this.manager = manager;
		
		// init streams
		try {
			inputStream = new ObjectInputStream(this.getInputStream());
			outputStream = new ObjectOutputStream(this.getOutputStream());
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	/**
	 * Constructor that inits the user object of the connection.
	 * 
	 * @param user
	 */
	public ClientConnection(ClientManager manager, HMUUser user) {
		this.manager = manager;
		this.user = user;
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
	
	/*
	 * Methods
	 */
	
	/**
	 * starts listening for messages on a seperate thread.
	 */
	public void listen() {
		Thread listener = new Thread(() -> {
			try {
				// infinite loop to listen to messages on.
				while(keepListening) {
					String input = (String) inputStream.readObject();
					
					// parse and handle the input.
					parseInput(input);
				}
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			catch(ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
			finally {
				// close connections
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
	}
	
	/**
	 * handles the message received by the Socket.
	 * 
	 * @param input
	 */
	public void parseInput(String input) {
		if(input.equals("[DISCONNECT]")) {
			
		}
	}
}
