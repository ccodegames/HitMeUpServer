package com.ccode.gui;

import com.ccode.network.ClientManager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * @author mcjcloud
 *
 */
public class HitMeUp extends Application {
	
	public static HitMeUp app;
	public static final int SERVER_PORT = 1234;
	
	// UI Components
	Scene appScene;
		BorderPane appPane;
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
		
		// start connection
		ClientManager manager = new ClientManager();
		
	}
	
	/**
	 * appends text to the console
	 * 
	 * @param message - the text to append.
	 */
	public void display(String message) {
		console.appendText(message + "\n");
	}

	/**
	 *  handle the application closing, save resources and exit application.
	 */
	@Override
	public void stop() {
		// the window was closed.
		// TODO: save resources.
		System.exit(0);
	}
}
