package controller;

import javafx.stage.Stage;
import model.Category;
import model.CategoryDAO;
import model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddProductController {
    private Stage primaryStage;
    private Connection conn = new Database().getConnection();

    public AddProductController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public List<Category> populateDropdown() {
        return  new CategoryDAO(conn).getCategory();

    }

}
