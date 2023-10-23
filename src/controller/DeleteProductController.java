package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.*;
import view.UpdateDeleteProduct_View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeleteProductController {
	private Stage primaryStage;
    private UpdateDeleteProduct_View view;
    private Database db;
    private Connection conn;
    
    public DeleteProductController(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        this.view = new UpdateDeleteProduct_View(this.primaryStage);
        this.view.ShowUpdateDeleteProductScene();
        db = new Database();
        conn = db.getConnection();
        fillCategory(populateDropdownCategory());
        fillSupplier(populateDropdownSupplier());

        populateTable();
        view.getSubmitButton().setOnAction(actionEvent -> {
        	 Product selectedProduct = view.getProductTableView().getSelectionModel().getSelectedItem();
             deleteToDatabase(selectedProduct);

                }
                );
    }
    
    private void fillCategory(List<Category> categoryList) {
        ObservableList<String> categoryString = FXCollections.observableArrayList();
        for (Category cat : categoryList
        ) {
            categoryString.add(cat.getCategoryName());
        }
        view.getCategoryComboBox().setItems(categoryString);
    }

    private List<Category> populateDropdownCategory() {
        return  getCategory();
    }

    private void fillSupplier(List<Supplier> supplierList) {
        ObservableList<String> categoryString = FXCollections.observableArrayList();
        for (Supplier cat : supplierList
        ) {
            categoryString.add(cat.getSupplierName());
        }
        view.getSupplierComboBox().setItems(categoryString);
    }

    private List<Supplier> populateDropdownSupplier() {
        return getSupplier();
    }

    private void populateTable() {
        view.getProductIdColumn().setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        view.getProductNameColumn().setCellValueFactory(productStringCellDataFeatures -> productStringCellDataFeatures.getValue().getProductName());
        view.getQuantityColumn().setCellValueFactory(productStringCellDataFeatures -> productStringCellDataFeatures.getValue().getProductPrice().asObject());
        List<Product> products = getProducts();
        ObservableList<Product> productObservableList = FXCollections.observableArrayList(products);
        view.getProductTableView().setItems(productObservableList);

    }

    private int getCategoryID(String cat) {
        String query = "SELECT category.id FROM category WHERE categoryName = ?";
        int id = 1;
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cat);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("category.id");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private int getSupplierID(String sup){
        String query = "SELECT supplier.id FROM supplier WHERE supplierName = ?";
        int id = 1;
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,sup);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("supplier.id");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<Supplier> getSupplier() {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM supplier";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                suppliers.add(new Supplier(rs.getInt("id"),
                        rs.getString("supplierName"),
                        rs.getString("supplierAddress"),
                        rs.getString("supplierPhone"),
                        rs.getString("payment_terms"),
                        rs.getInt("categoryID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public void deleteToDatabase(Product product) {
        if (product != null) {
            int productId = product.getId().get();
            String productName = product.getProductName().get();

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Delete Confirmation");
            confirmationAlert.setHeaderText("Delete Product");
            confirmationAlert.setContentText("Are you sure you want to delete the product:\n" + productName);

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed the deletion
                String deleteQuery = "DELETE FROM product WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                    pstmt.setInt(1, productId);
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        // Deletion was successful
                        view.getProductTableView().getItems().remove(product);
                        showDeleteSuccess();
                    } else {
                        // Handle the case where the deletion failed, e.g., show an error message.
                        showDeleteFailureAlert();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showDeleteFailureAlert();
                }
            }
        }
    }
    
    private void showDeleteFailureAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Deletion Error");
        alert.setHeaderText("Failed to delete the product.");
        alert.setContentText("An error occurred while deleting the product.");
        alert.showAndWait();
    }
    
    private void showDeleteSuccess() {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Success");
        alert.setHeaderText(null);
        alert.setContentText("The product has been successfully deleted.");
        alert.showAndWait();
    }
    

}
