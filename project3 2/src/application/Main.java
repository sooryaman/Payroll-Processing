
package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
The Main class is the driver class for payroll processing that sets the stage for the application and connects the 
FXML file to the rest of the package 
@author Bhumit Patel
@author Soorya Srivatsa
*/
public class Main extends Application {
	
	/**
	This is the method that creates the GUI and uses the FXML file to create the desired layout of the application
	@param primaryStage This is the main application window of the GUI
	*/
	@Override
	public void start(Stage primaryStage) {
		
		/*
		The following try block creates the application window and uses the FXML file to create the layout of the GUI
		*/
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("View.fxml"));
			Scene scene = new Scene(root, 600, 700);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Payroll Processing"); //setting the title of the application
			primaryStage.show();  //displaying the application
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
