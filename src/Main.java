import javafx.application.Application;
import javafx.stage.Stage;
import model.Database;
import view.Login_View;
import controller.LoginController;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FreshFind Inventory Management System");

        // Create an instance of the LoginController
        LoginController loginController = new LoginController(primaryStage);

        // Create an instance of the Login_View, passing the controller
        Login_View loginView = new Login_View(primaryStage, loginController);

        // Start the login view
        loginView.showLoginScene(); 
        
        
        
//        try {
//			con.getData();
//			
//			while(con.resultSet.next()) {
//				System.out.println(con.resultSet.getString("productName"));
//			}
//
//		}catch (Exception e) {
//			e.printStackTrace();
//		}

	
    }
}
