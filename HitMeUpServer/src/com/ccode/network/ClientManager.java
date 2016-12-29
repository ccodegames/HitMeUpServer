package com.ccode.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
				ServerSocket server = new ServerSocket(1234);
				clients = new ArrayList<ClientConnection>();
				// infinite loop to accept connections in.
				while(listen) {
					System.out.println("listening...");
					clients.add(new ClientConnection(this, server.accept()));
					
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
	
	// getter/setter
	public ArrayList<ClientConnection> getClients() {
		return clients;
	}
	public void addClient(ClientConnection connection) {
		clients.add(connection);
	}
	// remove a client (disconnect)
	public void disconnect(ClientConnection connection) {
		clients.remove(connection);
	}
	
	
}
