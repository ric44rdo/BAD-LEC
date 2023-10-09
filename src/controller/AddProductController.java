package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    }

    private void fillCategory(List<Category> categoryList) {
        ObservableList<String> categoryString = FXCollections.observableArrayList();
        for (Category cat: categoryList
        ) {
            categoryString.add(cat.getCategoryName());
        }
        view.getCategoryComboBox().setItems(categoryString);
    }

    private List<Category> populateDropdownCategory() {
        return  new CategoryDAO().getCategory();
    }

    private void fillSupplier(List<Supplier> supplierList) {
        ObservableList<String> categoryString = FXCollections.observableArrayList();
        for (Supplier cat: supplierList
        ) {
            categoryString.add(cat.getSupplierName());
        }
        view.getSupplierComboBox().setItems(categoryString);
    }

    private List<Supplier> populateDropdownSupplier() {
        return  new SupplierDAO().getSupplier();
    }
    public void populateTable() {
     view.getProductIdColumn().setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
     view.getProductNameColumn().setCellValueFactory(productStringCellDataFeatures -> productStringCellDataFeatures.getValue().getProductName());
     view.getQuantityColumn().setCellValueFactory(productStringCellDataFeatures -> productStringCellDataFeatures.getValue().getProductPrice().asObject());
     List<Product> products = new ProductDAO().getProducts();
     ObservableList<Product> productObservableList = FXCollections.observableArrayList(products);
     view.getProductTableView().setItems(productObservableList);

    }

}
