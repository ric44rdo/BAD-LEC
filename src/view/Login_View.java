package view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import controller.LoginController; // Import the LoginController

public class Login_View {

    private TextField emailField;
    private PasswordField passwordField;
    private Button loginButton;
    private Hyperlink registerLink;
    private Stage primaryStage;
    private LoginController loginController; // Add a reference to the LoginController

    public Login_View(Stage primaryStage, LoginController loginController) {
        this.primaryStage = primaryStage;
        this.loginController = loginController; // Initialize the LoginController
    }

    public void showLoginScene() {
        primaryStage.setTitle("FreshFind Inventory Management System");

        // Create center content
        GridPane centerGrid = createLoginForm();

        // Create top content
        Text titleText = new Text("Login Form");
        titleText.setFont(Font.font("Arial Black", 26));

        // Adjust spacing and padding
        VBox.setMargin(titleText, new Insets(10)); // Add space around the title
        centerGrid.setPadding(new Insets(20)); // Add space around the grid

        // Create layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titleText);
        BorderPane.setAlignment(titleText, Pos.CENTER);
        borderPane.setCenter(centerGrid);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(borderPane);
        vbox.setPrefSize(640, 400);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createLoginForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label emailLabel = new Label("Email:");
        Label passwordLabel = new Label("Password:");

        emailField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Login");
        registerLink = new Hyperlink("Don't Have an Account? Register");

        grid.add(emailLabel, 0, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(emailField, 1, 0);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(registerLink, 1, 3);

        loginButton.setOnAction(event -> handleLoginButtonClick());

        return grid;
    }

    private void handleLoginButtonClick() {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Call the loginController to handle the login
        loginController.handleLoginButtonClick(email, password);
    }
}

    
    


