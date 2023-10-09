package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.*;
import view.AddProduct_View;

import java.util.List;

public class AddProductController {
    private Stage primaryStage;
    private AddProduct_View view;


    public AddProductController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.view = new AddProduct_View(this.primaryStage);
        this.view.ShowAddProductScene();
        fillCategory(populateDropdownCategory());
        fillSupplier(populateDropdownSupplier());
        populateTable();

        view.getSubmitButton().setOnAction(actionEvent ->
                insertToDatabase());
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
        return new CategoryDAO().getCategory();
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
        return new SupplierDAO().getSupplier();
    }

    private void populateTable() {
        view.getProductIdColumn().setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        view.getProductNameColumn().setCellValueFactory(productStringCellDataFeatures -> productStringCellDataFeatures.getValue().getProductName());
        view.getQuantityColumn().setCellValueFactory(productStringCellDataFeatures -> productStringCellDataFeatures.getValue().getProductPrice().asObject());
        List<Product> products = new ProductDAO().getProducts();
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

            ProductDAO productDAO = new ProductDAO();
            productDAO.addProduct(productName, categoryName, productPrice, productQuantity, supplierName);
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
            alertSuccess.setTitle("Success");
            alertSuccess.setHeaderText("Successfully added!");
            alertSuccess.showAndWait();
            new AddProductController(primaryStage);
        }

    }


}

