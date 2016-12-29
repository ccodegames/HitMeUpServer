package com.ccode.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.ccode.model.HMUUser;

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
			// construct and flush output stream
			outputStream = new ObjectOutputStream(this.socket.getOutputStream());
			outputStream.flush();
			
			// construct inputStream
			inputStream = new ObjectInputStream(this.socket.getInputStream());
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
	
	/*
	 * Methods
	 */
	
	public void sendMessage(String message) {
		Thread sender = new Thread(() -> {
			byte[] output = (message + "[END]").getBytes();
			try {
				outputStream.write(output);
				outputStream.flush();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
		});
		sender.start();
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
						sendMessage(finalMessage);
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
		if(input.equals("[DISCONNECT]")) {
			
		}
		else {
			System.out.println("parsing string: " + input);
		}
	}
}
