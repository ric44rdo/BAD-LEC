package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private Connection conn;

    public CategoryDAO(Connection conn) {
        //Open connection to DB
        this.conn = conn;
    }

    //Generate List from Database
    public List<Category> getCategory() throws SQLException {
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


