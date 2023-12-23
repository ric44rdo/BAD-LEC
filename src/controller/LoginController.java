package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User; 
import model.Database;
import view.UpdateDeleteProduct_View;

public class LoginController {

    private Stage primaryStage;
    private Database db;
    
    public LoginController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.db = new Database();
    }

    public  boolean isValid(String email, String password) {
    	boolean result = false;
    	Connection connection = db.getConnection();
    	
    	if(connection != null) {
    		try {
				String sql = "SELECT * FROM User WHERE email = ? AND password = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, password);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					result = true;
				}else {
					result = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
		return result;
    }


    public void handleLoginButtonClick(String email, String password) {
        // Handle the login button click event
        
        // Check if the provided credentials are valid
        if (isValid(email, password)) {
            // Redirect to the home page
        	DeleteProductController deleteProductController = new DeleteProductController(primaryStage);
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
