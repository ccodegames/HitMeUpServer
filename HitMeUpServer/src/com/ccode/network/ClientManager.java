package com.ccode.network;

import java.net.ServerSocket;
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
				ServerSocket server = new ServerSocket(8080);
				clients = new ArrayList<ClientConnection>();
				// infinite loop to accept connections in.
				while(listen) {
					clients.add((ClientConnection) server.accept());
					
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
	
	// remove a client (disconnect)
	public void disconnect(ClientConnection connection) {
		clients.remove(connection);
	}
}
