package view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import model.Product;

public class UpdateDeleteProduct_View{

    private TextField searchField;
    private Button searchButton;
    private Label titleLabel;
    private TextField productNameField;
    private ComboBox<String> categoryComboBox;
    private TextField priceField;
    private TextField quantityField;
    private TextField supplierField;
    private Button submitButton;
    private TableView<Product> productTableView;
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
        TableColumn<Product, String> productIdColumn = new TableColumn<>("Product ID");
        TableColumn<Product, String> productNameColumn = new TableColumn<>("Product Name");
        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        tableView.getColumns().addAll(productIdColumn, productNameColumn, quantityColumn);
        tableView.setPrefHeight(310);
        tableView.setPrefWidth(281);
        return tableView;
    }

    private VBox createAddProductBox() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(0, 20, 0, 20));
        vbox.setSpacing(10);
        Label titleLabel = new Label("Add New Product");
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
        supplierField = new TextField();
        submitButton = new Button("Submit");

        grid.add(productNameLabel, 0, 0);
        grid.add(categoryLabel, 0, 1);
        grid.add(priceLabel, 0, 2);
        grid.add(quantityLabel, 0, 3);
        grid.add(supplierLabel, 0, 4);
        grid.add(productNameField, 1, 0);
        grid.add(categoryComboBox, 1, 1);
        grid.add(priceField, 1, 2);
        grid.add(quantityField, 1, 3);
        grid.add(supplierField, 1, 4);
        grid.add(submitButton, 1, 5);

        return grid;
    }
}
