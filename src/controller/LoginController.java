package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User; 
import model.Database;
import view.UpdateDeleteProduct_View;

public class LoginController {

    private Stage primaryStage;
    
    public LoginController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public boolean isValid(String email, String password) {
        // Check if the provided email and password are valid
        // This is where you should implement your authentication logic
        // For example, you can check them against a database or another authentication system
        // Return true if valid, false otherwise
        
        // Replace this with your actual authentication logic
        // For demonstration purposes, let's assume a hardcoded user for testing:
//    	User user = new User(email,password);
//    	return user.getEmail().equals(email) && user.getPassword().equals(password);
    	return true; 
    }

    public void handleLoginButtonClick(String email, String password) {
        // Handle the login button click event
        
        // Check if the provided credentials are valid
        if (isValid(email, password)) {
            // Redirect to the home page
            UpdateDeleteProduct_View homePage = new UpdateDeleteProduct_View(primaryStage);
            homePage.ShowUpdateDeleteProductScene();
        } else {
            // Display an error message
            showError("Invalid credentials. Please try again.");
        } 
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
