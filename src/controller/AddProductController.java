package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.*;
import view.AddProduct_View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddProductController {
    private Stage primaryStage;
    private AddProduct_View view;
    private Database db;
    private Connection conn;


    public AddProductController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.view = new AddProduct_View(this.primaryStage);
        this.view.ShowAddProductScene();
        db = new Database();
        conn = db.getConnection();
        fillCategory(populateDropdownCategory());
        fillSupplier(populateDropdownSupplier());

        populateTable();
        db.closeConnection();
        view.getSubmitButton().setOnAction(actionEvent -> {

                    insertToDatabase();

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

    private void insertToDatabase() {

        String productName = view.getProductNameField().getText();
        String categoryName = view.getCategoryComboBox().getValue();

        String productPriceTemp = view.getPriceField().getText();
        Integer productPrice = 0;
        if (!productPriceTemp.isEmpty()) {
            try {
                productPrice = Integer.valueOf(productPriceTemp);
            } catch (NumberFormatException e) {
                Alert alertFail = new Alert(Alert.AlertType.ERROR);
                alertFail.setTitle("Fail");
                alertFail.setHeaderText("Failed to add Product");
                alertFail.setContentText("Quantity and Price must be a number more than 0");
                alertFail.showAndWait();
                return;
            }
        }
        String productQuantityTemp = view.getQuantityField().getText();
        Integer productQuantity = 0;
        if (!productQuantityTemp.isEmpty()) {
            try {
                productQuantity = Integer.valueOf(productQuantityTemp);
            } catch (NumberFormatException e) {
                Alert alertFail = new Alert(Alert.AlertType.ERROR);
                alertFail.setTitle("Fail");
                alertFail.setHeaderText("Failed to add Product");
                alertFail.setContentText("Quantity and Price must be a number more than 0");
                alertFail.showAndWait();
                return;
            }
        }
        String supplierName = view.getSupplierComboBox().getValue();
        if (
                productName.isEmpty() || productName == null ||
                        categoryName == null ||
                        productPrice == null || productPrice <= 0 ||
                        productQuantity == null || productQuantity <= 0 ||
                        supplierName == null

        ) {
            Alert alertFail = new Alert(Alert.AlertType.ERROR);
            alertFail.setTitle("Fail");
            alertFail.setHeaderText("Failed to add Product");
            alertFail.setContentText("Ensure that all fields are filled.");
            alertFail.showAndWait();

        } else {


            addProduct(productName, categoryName, productPrice, productQuantity, supplierName);
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
            alertSuccess.setTitle("Success");
            alertSuccess.setHeaderText("Successfully added!");
            alertSuccess.showAndWait();
            new AddProductController(primaryStage);
        }

    }

    public void addProduct(String productName, String categoryName, int productPrice, int productQuantity, String supplierName) {
        db.connect();
        conn = db.getConnection();
        String query = "INSERT INTO product(productName,categoryID,productPrice,productQuantity,supplierID) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, productName);
            pstmt.setInt(2, getCategoryID(categoryName));
            pstmt.setInt(3, productPrice);
            pstmt.setInt(4, productQuantity);
            pstmt.setInt(5, getSupplierID(supplierName));

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
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
            pstmt.close();
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
            pstmt.close();
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
            pstmt.close();
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
            pstmt.close();
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
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

}




