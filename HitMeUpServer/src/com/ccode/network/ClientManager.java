package com.ccode.network;

import java.net.ServerSocket;
import java.util.ArrayList;

import com.ccode.gui.HitMeUp;
import com.ccode.model.HMUUser;

import javafx.scene.paint.Color;

import static com.ccode.gui.HitMeUp.app;

/**
 * ClientManager manages the connections with multiple clients.
 * The class will accept connections on a thread other than the main and create
 * a ClientConnection object.
 * 
 * @author mcjcloud
 *
 */
public class ClientManager {
	
	private ArrayList<ClientConnection> clients;
	private boolean listen = true;

	public ClientManager() {
		
		Thread serverThread = new Thread(() -> {
			try {
				ServerSocket server = new ServerSocket(HitMeUp.SERVER_PORT);
				app.setStatus("Online", Color.GREEN);
				clients = new ArrayList<ClientConnection>();
				// infinite loop to accept connections in.
				app.display("Listening...");
				while(listen) {
					ClientConnection client = new ClientConnection(this, server.accept());
					clients.add(client);
					app.display("client connected: " + client.getSocket().getInetAddress().getHostAddress());
					
					System.out.println("clients: " + clients.toString());
				}
				server.close();
			}
			catch(Exception e) {
				// TODO: handle exception for real
				e.printStackTrace();
			}
		});
		serverThread.start();
	}
	
	// stop the server from listening for connections.
	public void stop() {
		this.listen = false;
	}
	
	/*
	 *  getter/setter
	 */
	public ArrayList<ClientConnection> getClients() {
		return clients;
	}
	public void addClient(ClientConnection connection) {
		clients.add(connection);
	}
	// remove a client (disconnect)
	public void disconnect(ClientConnection connection) {
		clients.remove(connection);
		app.display("client disconnected: " + connection.getSocket().getInetAddress().getHostAddress());
	}
	public void disconnectAll() {
		clients.clear();
		app.display("All Clients disconnected.");
	}
	
	/**
	 * Get the ClientConnections with the given User associated.
	 * 
	 * @param user
	 * @return
	 */
	public ArrayList<ClientConnection> getClients(HMUUser user) {
		ArrayList<ClientConnection> result = new ArrayList<ClientConnection>();
		for(ClientConnection conn : clients) {
			// if the the username with the connection equals the username provided.
			if(conn.getUser().getUsername().equals(user.getUsername())) {
				result.add(conn);	// add the connection to the result array.
			}
		}
		
		// return the resultant array
		return result;
	}
}
