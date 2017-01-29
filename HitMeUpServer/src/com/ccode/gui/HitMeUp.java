package com.ccode.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.ccode.model.HMUUser;
import com.ccode.model.Mood;
import com.ccode.model.data.DataManager;
import com.ccode.network.ClientManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author mcjcloud
 *
 */
public class HitMeUp extends Application {
	
	public static HitMeUp app;
	public static final int SERVER_PORT = 1234;
	
	//private static ArrayList<HMUUser> users;
	
	// UI Components
	Scene appScene;
		BorderPane appPane;
			Label statusLabel;
			TilePane optionPane;
				// options
				Button restartButton;
				Button stopButton;
				Button kickAllButton;
			// messages about what is happening in the server.
			TextArea console;
			// shows a list of connections
			ScrollPane connections;
	
	/**
	 * main method, launch the GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	
	@Override
	public void start(Stage window) throws Exception {
		// Entry point for application GUI
		HitMeUp.app = this; 
		
		appPane = new BorderPane();
		
		// setup statusLabel
		statusLabel = new Label();
		statusLabel.setMaxWidth(Double.MAX_VALUE);
		statusLabel.setTextAlignment(TextAlignment.CENTER);
		appPane.setTop(statusLabel);
		
		// setup options pane
		optionPane = new TilePane(Orientation.VERTICAL);
		optionPane.setVgap(5.0);
		
		restartButton = new Button("Restart");
		restartButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		restartButton.setOnAction(e -> {
			// TODO: restart server.
		});
		optionPane.getChildren().add(restartButton);
		
		stopButton = new Button("Stop");
		stopButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		stopButton.setOnAction(e -> {
			// shut down server.
			this.stop();
		});
		optionPane.getChildren().add(stopButton);
		
		kickAllButton = new Button("Kick All");
		kickAllButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		kickAllButton.setOnAction(e -> {
			// TODO: kick all
		});
		optionPane.getChildren().add(kickAllButton);
		appPane.setLeft(optionPane);
		
		// setup console
		console = new TextArea();
		console.setEditable(false);
		appPane.setCenter(console);
		
		// TODO: setup connections list
		
		// create Scene and show
		appScene = new Scene(appPane, 800, 600);
		window.setScene(appScene);
		window.show();
		
		// start logic
		display("Booting up...");
		//users = new ArrayList<HMUUser>();
		
		// start connection
		ClientManager manager = new ClientManager();
		
		boolean loaded = false;
		try {
			loaded = DataManager.loadUsers();
		} catch(Exception ioe) {
			HitMeUp.app.display("Error loading users.");
		}
		System.out.println("loaded users: " + loaded);
		// TODO: remove test user
		HMUUser testUser = new HMUUser("mcjcloud", "brayden1", "Brayden", "Cloud", "brayden14cloud@gmail.com");
		testUser.setMood(Mood.Happy);
		System.out.println("test user: " + testUser);
		DataManager.addUser(testUser);
		//DataManager.removeUser(testUser);
		System.out.println("created user");
		HitMeUp.app.display("users: " + DataManager.getUsers());
	}
	
	/**
	 * appends text to the console
	 * 
	 * @param message - the text to append.
	 */
	public void display(String message) {
		Platform.runLater(() -> {
			console.appendText(message + "\n");
		});
	}
	
	/**
	 * show the status of the server.
	 * 
	 * @param status - a string representing the status of the server
	 */
	public void setStatus(String status, Color color) {
		Platform.runLater(() -> {
			statusLabel.setText(status);
			statusLabel.setTextFill(color);
		});
	}

	/**
	 *  handle the application closing, save resources and exit application.
	 */
	@Override
	public void stop() {
		// the window was closed.
		// TODO: save resources.
		DataManager.removeAllUsers();	// TODO: remove this.
		try {
			DataManager.saveUsers();
		}
		catch(IOException ioe) {
			app.display("Error saving data...");
		}
		
		System.exit(0);
	}
	
	/*
	 * Getter/setter
	 */
	/*
	public static ArrayList<HMUUser> getUsers() {
		return users;
	}
	public static void addUser(HMUUser user) {
		users.add(user);
	}
	public static void removeUser(HMUUser user) {
		users.remove(user);
	}
	
	public static ArrayList<HMUUser> getUsers(String[] usernames) {
		// get HMUUser objects
		ArrayList<HMUUser> result = new ArrayList<HMUUser>();
		for(String userString : usernames) {
			for(HMUUser user : HitMeUp.getUsers()) {
				if(user.getUsername().equals(userString)) {
					result.add(user);
				}
			}
		}
		
		return result;
	}
	*/
}
