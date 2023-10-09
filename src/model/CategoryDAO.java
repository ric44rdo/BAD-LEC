package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private Database db;
    private Connection conn;

    public CategoryDAO() {
        //Open connection to DB
        db = new Database();
        db.connect();
        conn = db.getConnection();
    }

    //Generate List from Database
    public List<Category> getCategory() {
        List<Category> category = new ArrayList<>();
        String query = "SELECT * FROM category";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                category.add(new Category(
                        rs.getInt("id"),
                        rs.getString("categoryName")
                ));
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }


}


