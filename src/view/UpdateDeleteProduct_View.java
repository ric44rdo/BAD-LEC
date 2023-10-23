package view;

import controller.AddProductController;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Product;

import java.sql.SQLException;

public class UpdateDeleteProduct_View{

    private TextField searchField;
    private Button searchButton;
    private Label titleLabel;
    private TextField productNameField;
    private ComboBox<String> categoryComboBox;
    private TextField priceField;
    private TextField quantityField;
    private TextField supplierField;
    private Button updateButton; 
    private Button deleteButton;
    private FlowPane buttonWrapper;
    private TableView<Product> productTableView;
    private ComboBox<String> supplierComboBox;
    private TableColumn<Product, Integer> quantityColumn;
    private TableColumn<Product, String> productNameColumn;
    private TableColumn<Product, Integer> productIdColumn;
    private Stage primaryStage;

    public UpdateDeleteProduct_View(Stage primaryStage){
    	this.primaryStage = primaryStage;
    }

    public void ShowUpdateDeleteProductScene() {
        primaryStage.setTitle("FreshFind Inventory Management System");

        // Create menu bar and menus
        Menu menuProduct = new Menu("Product Management");
        MenuItem addProductItem = new MenuItem("Add New Product");
        MenuItem updateProductItem = new MenuItem("Update or Remove Product");
        menuProduct.getItems().addAll(addProductItem, updateProductItem);

        Menu menuSupplier = new Menu("Supplier Management");
        MenuItem addSupplierItem = new MenuItem("Add New Supplier");
        MenuItem updateSupplierItem = new MenuItem("Update or Remove Supplier");
        menuSupplier.getItems().addAll(addSupplierItem, updateSupplierItem);

        Menu menuProfile = new Menu("Profile");

        MenuBar menuBar = new MenuBar(menuProduct, menuSupplier, menuProfile);

        // Create center content
        productTableView = createProductTableView();

        // Create top content
        HBox searchBox = createSearchBox();

        // Create right content
        VBox addProductBox = createAddProductBox();

        // Create layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(searchBox);
        BorderPane.setAlignment(searchBox, Pos.CENTER);
        BorderPane.setMargin(productTableView, new Insets(0, 10, 10, 10)); // Add margin
        borderPane.setCenter(productTableView);
        borderPane.setRight(addProductBox);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar,borderPane);
        vbox.setPrefSize(640, 400);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
        
//      Create an event handler for the "Add New Product" menu item
        EventHandler<ActionEvent> addProductHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    handleAddProductMenuItemClick();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        };

        // Attach the event handler to the "Add New Product" menu item
        addProductItem.setOnAction(addProductHandler);
    }

    private HBox createSearchBox() {
        searchField = new TextField();
        searchField.setPromptText("Search");
        searchButton = new Button("Search");
        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setSpacing(10);
        searchBox.setPadding(new Insets(14, 0, 14, 28));
        return searchBox;
    }

    private TableView<Product> createProductTableView() {
        TableView<Product> tableView = new TableView<>();
        productIdColumn = new TableColumn<>("Product ID");
        productNameColumn = new TableColumn<>("Product Name");
        quantityColumn = new TableColumn<>("Quantity");
        tableView.getColumns().addAll(productIdColumn, productNameColumn, quantityColumn);
        tableView.setPrefHeight(310);
        tableView.setPrefWidth(281);
        
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Handle the selection here and populate your form fields
                handleProductSelection(newSelection);
            }
        });
        
        return tableView;
    }

    private VBox createAddProductBox() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(0, 20, 0, 20));
        vbox.setSpacing(10);
        Label titleLabel = new Label("Update or Delete Product");
        titleLabel.setPadding(new Insets(10, 0, 0, 0));
        GridPane grid = createAddProductForm();
        vbox.getChildren().addAll(titleLabel, grid);
        return vbox;
    }

    private GridPane createAddProductForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label productNameLabel = new Label("Product Name:");
        Label categoryLabel = new Label("Category:");
        Label priceLabel = new Label("Price:");
        Label quantityLabel = new Label("Quantity:");
        Label supplierLabel = new Label("Supplier:");

        productNameField = new TextField();
        categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("Pick Category");
        priceField = new TextField();
        quantityField = new TextField();
        supplierComboBox = new ComboBox<>();
        supplierComboBox.setPromptText("Pick Supplier");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        buttonWrapper = new FlowPane();
        buttonWrapper.getChildren().add(updateButton);
        buttonWrapper.getChildren().add(deleteButton);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        
        buttonWrapper.setAlignment(Pos.CENTER_LEFT); // Set alignment to center
        buttonWrapper.setHgap(3); // Set horizontal gap between nodes
        buttonWrapper.setVgap(3); // Set vertical gap between nodes
        buttonWrapper.setPrefWrapLength(Region.USE_PREF_SIZE);

        grid.add(productNameLabel, 0, 0);
        grid.add(categoryLabel, 0, 1);
        grid.add(priceLabel, 0, 2);
        grid.add(quantityLabel, 0, 3);
        grid.add(supplierLabel, 0, 4);
        grid.add(productNameField, 1, 0);
        grid.add(categoryComboBox, 1, 1);
        grid.add(priceField, 1, 2);
        grid.add(quantityField, 1, 3);
        grid.add(supplierComboBox, 1, 4);
        grid.add(buttonWrapper, 1, 5);
        

        return grid;
    }
    
    
    private void handleProductSelection(Product selectedProduct) {
        if (selectedProduct != null) {
            productNameField.setText(selectedProduct.getProductName().get());
            categoryComboBox.setValue(selectedProduct.getCategoryName().get());
            priceField.setText(String.valueOf(selectedProduct.getProductPrice().get()));
            quantityField.setText(String.valueOf(selectedProduct.getProductQuantity().get()));
            supplierComboBox.setValue(selectedProduct.getSupplierName().get());
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
            
            
        } else {
            // Handle the case where no product is selected (e.g., clear form fields)
            productNameField.clear();
            categoryComboBox.getSelectionModel().clearSelection();
            priceField.clear();
            quantityField.clear();
            // Clear other form fields as needed
        }
    }

    private void handleAddProductMenuItemClick() throws SQLException {

        AddProductController addProductController = new AddProductController(primaryStage);
    }
    
    public ComboBox<String> getCategoryComboBox() {
        return categoryComboBox;
    }

    public ComboBox<String> getSupplierComboBox() {
        return supplierComboBox;
    }

    public TableColumn<Product, Integer> getQuantityColumn() {
        return quantityColumn;
    }

    public TableColumn<Product, String> getProductNameColumn() {
        return productNameColumn;
    }

    public TableColumn<Product, Integer> getProductIdColumn() {
        return productIdColumn;
    }

    public TableView<Product> getProductTableView() {
        return productTableView;
    }

    public TextField getProductNameField() {
        return productNameField;
    }

    public TextField getPriceField() {
        return priceField;
    }

    public TextField getQuantityField() {
        return quantityField;
    }

    public Button getSubmitButton() {
        return deleteButton;
    }
}
