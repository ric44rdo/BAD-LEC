package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty productName = new SimpleStringProperty();
    private IntegerProperty categoryID = new SimpleIntegerProperty();
    private IntegerProperty productPrice = new SimpleIntegerProperty();
    private IntegerProperty productQuantity = new SimpleIntegerProperty();
    private IntegerProperty supplierID = new SimpleIntegerProperty();
    private Database db;
    private Connection conn;

    public Product(int id, String productName, int categoryID, int productPrice, int productQuantity, int supplierID) {
        this.id.set(id);
        this.productName.set(productName);
        this.categoryID.set(categoryID);
        this.productPrice.set(productPrice);
        this.productQuantity.set(productQuantity);
        this.supplierID.set(supplierID);
    }

    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getProductName() {
        return productName;
    }

    public StringProperty getCategoryName() {
        StringProperty category = new SimpleStringProperty();
        String query = "SELECT categoryName FROM category WHERE id = ?";
        db = new Database();
        conn = db.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, this.getCategoryID().get());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                category.set(rs.getString("categoryName"));
            }
        } catch (SQLException e) {
            System.err.println("Error: Failed to retrieve category name from the database.");
            e.printStackTrace();
        }
        return category;
    }
    
    public StringProperty getSupplierName() {
    	StringProperty category = new SimpleStringProperty();
    	String query = "SELECT supplierName FROM supplier WHERE id = ?";
    	db = new Database();
    	conn = db.getConnection();
    	try {
    		PreparedStatement pstmt = conn.prepareStatement(query);
    		pstmt.setInt(1, this.getSupplierID().get());
    		ResultSet rs = pstmt.executeQuery();
    		if (rs.next()) {
    			category.set(rs.getString("supplierName"));
    		}
    	} catch (SQLException e) {
    		System.err.println("Error: Failed to retrieve category name from the database.");
    		e.printStackTrace();
    	}
    	return category;
    }


    public IntegerProperty getProductPrice() {
        return productPrice;
    }

    public IntegerProperty getProductQuantity() {
        return productQuantity;
    }

    public IntegerProperty getSupplierID() {
        return supplierID;
    }
    
    public IntegerProperty getCategoryID() {
    	return categoryID;
    }

	
    
    
}
