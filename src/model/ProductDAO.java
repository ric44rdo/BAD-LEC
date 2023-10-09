package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Database db;
    private Connection conn;

    public ProductDAO() {
        //Open connection to DB
        db = new Database();
        db.connect();
        conn = db.getConnection();
    }

    //Generate List from Database
    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                        rs.getString("productName"),
                        rs.getInt("categoryID"),
                        rs.getInt("productPrice"),
                        rs.getInt("productQuantity"),
                        rs.getInt("supplierID")
                ));
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void addProduct(String productName, int categoryID, int productPrice, int productQuantity, int supplierID) throws SQLException {
        String query = "INSERT INTO product(productName,categoryID,productPrice,productQuantity,supplierID) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,productName);
            pstmt.setInt(2,categoryID);
            pstmt.setInt(3,productPrice);
            pstmt.setInt(4,productQuantity);
            pstmt.setInt(5,supplierID);

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
