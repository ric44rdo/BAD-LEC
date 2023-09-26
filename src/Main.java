import javafx.application.Application;
import javafx.stage.Stage;
import view.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FreshFind Inventory Management System");
/*
        // Create an instance of the Login_View
        Login_View loginView = new Login_View(primaryStage);

        // Start the login view
        loginView.showLoginScene();
*/        
        // Create an instance of the Login_View
        AddProduct_View loginView = new AddProduct_View(primaryStage);
        
        // Start the login view
        loginView.ShowAddProductScene();
    }
}
