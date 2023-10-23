package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {

	public Connection connection;
	public Statement statement;
	public ResultSet resultSet;
	public ResultSetMetaData resultMeta;
	public PreparedStatement preparedStatement;
	public ObservableList<Product> productData;
	
	public Database()  {
		productData = FXCollections.observableArrayList();
		
		try {
			String url = "jdbc:mysql://localhost:3306/bad_lab";
			String user = "root";
			String password = "";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
 
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	


	public void getData() {
		try {
			statement = connection.createStatement();
			
			preparedStatement = connection.prepareStatement("SELECT * FROM product;");
			
			resultSet = preparedStatement.executeQuery();
			resultMeta = resultSet.getMetaData();

			
			while (resultSet.next()) {
			    Supplier supplier = new Supplier("Nama Supplier"); // Gantilah "Nama Supplier" dengan nama supplier yang sesuai
			    productData.add(new Product(
			        resultSet.getInt("id"),
			        resultSet.getString("productName"),
			        resultSet.getInt("productQuantity"),
			        resultSet.getInt("productPrice"),
			        supplier
			    ));
			}
		}catch (Exception e) {
			//TODO: handle exception
			System.out.println("failed to load to get data");
		}
		
	
	}



	public Connection getConnection() {
        return connection;
    }
	
	
	
	
	

	
	
}	


//public ResultSet getData() {
//try {
//	statement = connection.createStatement();
//	String sql = "SELECT * FROM product";
//	
//	resultSet = statement.executeQuery(sql);
//	this.productData = FXCollections.observableArrayList();
//	
//	
//
//}catch (Exception e) {
//	//TODO: handle exception
//	System.out.println("failed to load to get data");
//}
//
//return resultSet;
//
//
//}
	
