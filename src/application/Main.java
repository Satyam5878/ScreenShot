package application;

import javafx.application.Application;
import javafx.stage.Stage;



// JavaFx Main Application
public class Main extends Application {
	// this will be called by jvm
	public static void main(String[] args) {
		Application.launch(args);
	}

	// Java fx framework will call this method
	public void start(Stage stage) throws Exception {
		new NewInstance(stage);		// This creates the new instance of the application.

	}
}
