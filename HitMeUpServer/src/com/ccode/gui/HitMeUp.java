package com.ccode.gui;

import com.ccode.network.ClientManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class HitMeUp extends Application {
	
	public static final int SERVER_PORT = 8080;
	
	/**
	 * main method, launch the GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Entry point for application GUI
		
		/*	TODO remove this reference code.
		ServerSocket sSocket = new ServerSocket(SERVER_PORT);
		Socket connection = sSocket.accept();
		sSocket.close();
		
		System.out.println("connection made: " + connection.toString());
		*/
		
		ClientManager manager = new ClientManager();
	}

}
